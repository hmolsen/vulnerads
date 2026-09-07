#!/usr/bin/env bash
#
# Point IntelliJ's "Gradle JVM" at a Java 17+ JDK, without opening the IDE.
#
# Why this is needed: Gradle 9.7.1 refuses to run on a JVM older than 17. That
# JVM is chosen by IntelliJ's "Gradle JVM" setting, which is INDEPENDENT of the
# Java toolchain in build.gradle. If it points at an old JDK, IntelliJ fails to
# sync with:
#
#   Your build is currently configured to use incompatible Java 13.x and
#   Gradle 9.7.1. Cannot sync the project.
#
# Do NOT take IntelliJ's suggestion to downgrade to Gradle 8.14 - Spring Boot 4
# requires Gradle 9.
#
# Usage:   ./configure-intellij-gradle-jvm.sh [/path/to/project] [/path/to/jdk]
#
# IntelliJ must be CLOSED: it rewrites these files on exit and would discard
# whatever this script sets.

set -euo pipefail

PROJECT_DIR="${1:-$(cd "$(dirname "$0")" && pwd)}"
EXPLICIT_JDK="${2:-}"

log()  { printf '%s\n' "$*"; }
fail() { printf 'ERROR: %s\n' "$*" >&2; exit 1; }

# --- refuse to run while the IDE is open ------------------------------------
if pgrep -f "[i]dea" >/dev/null 2>&1; then
    fail "IntelliJ appears to be running. Close it first - it overwrites these files on exit."
fi

# --- locate a JDK 17+ --------------------------------------------------------
jdk_version() {   # $1 = jdk home -> major version on stdout, empty if unreadable
    local rel="$1/release" v=""
    if [ -r "$rel" ]; then
        v=$(sed -n 's/^JAVA_VERSION="\([0-9][0-9]*\).*/\1/p' "$rel" | head -1)
    fi
    if [ -z "$v" ] && [ -x "$1/bin/javac" ]; then
        v=$("$1/bin/javac" -version 2>&1 | sed -n 's/^javac \([0-9][0-9]*\).*/\1/p' | head -1)
    fi
    printf '%s' "$v"
}

CANDIDATES=()
[ -n "$EXPLICIT_JDK" ] && CANDIDATES+=("$EXPLICIT_JDK")
[ -n "${JAVA_HOME:-}" ] && CANDIDATES+=("$JAVA_HOME")
if command -v javac >/dev/null 2>&1; then
    CANDIDATES+=("$(dirname "$(dirname "$(readlink -f "$(command -v javac)")")")")
fi
for d in /usr/lib/jvm/*/ /usr/lib/jvm/.*/ /opt/java/*/ "$HOME"/.jdks/*/; do
    [ -d "$d" ] && CANDIDATES+=("${d%/}")
done

JDK_HOME=""
JDK_VER=""
for c in "${CANDIDATES[@]}"; do
    [ -d "$c" ] || continue
    [ -x "$c/bin/java" ] || continue
    v=$(jdk_version "$c")
    [ -n "$v" ] || continue
    if [ "$v" -ge 17 ] 2>/dev/null; then
        # prefer the lowest qualifying version for maximum compatibility
        if [ -z "$JDK_VER" ] || [ "$v" -lt "$JDK_VER" ]; then
            JDK_HOME="$c"; JDK_VER="$v"
        fi
    fi
done

[ -n "$JDK_HOME" ] || fail "No JDK 17 or newer found. Install one (e.g. 'sudo apt install openjdk-17-jdk') or pass its path as the 2nd argument."
log "Using JDK $JDK_VER at: $JDK_HOME"

# --- register the JDK in IntelliJ's SDK table and select it ------------------
python3 - "$PROJECT_DIR" "$JDK_HOME" "$JDK_VER" <<'PY'
import glob, os, sys
import xml.etree.ElementTree as ET

project, jdk_home, jdk_ver = sys.argv[1], sys.argv[2], sys.argv[3]
sdk_name = "jdk-%s" % jdk_ver

def save(tree, path):
    os.makedirs(os.path.dirname(path), exist_ok=True)
    tree.write(path, encoding="UTF-8", xml_declaration=True)

# 1) IDE SDK table: add an entry for this JDK if absent -----------------------
homes = sorted(glob.glob(os.path.expanduser("~/.config/JetBrains/*"))) + \
        sorted(glob.glob(os.path.expanduser("~/.IntelliJIdea*/config")))
ide_dirs = [d for d in homes if os.path.isdir(d) and
            os.path.basename(d).lower().startswith(("intellijidea", "ideaic", "ideau", "config"))]
if not ide_dirs:
    ide_dirs = [d for d in homes if os.path.isdir(d)]

registered = []
for d in ide_dirs:
    tbl = os.path.join(d, "options", "jdk.table.xml")
    if os.path.exists(tbl):
        tree = ET.parse(tbl); root = tree.getroot()
        comp = root.find("./component[@name='ProjectJdkTable']")
        if comp is None:
            comp = ET.SubElement(root, "component", {"name": "ProjectJdkTable"})
    else:
        root = ET.Element("application")
        comp = ET.SubElement(root, "component", {"name": "ProjectJdkTable"})
        tree = ET.ElementTree(root)

    # already present (same homePath)? then just reuse its name
    existing = None
    for jdk in comp.findall("jdk"):
        hp = jdk.find("homePath")
        if hp is not None and os.path.realpath(hp.get("value", "")) == os.path.realpath(jdk_home):
            nm = jdk.find("name")
            existing = nm.get("value") if nm is not None else None
            break
    if existing:
        registered.append((d, existing, "already present"))
        continue

    jdk = ET.SubElement(comp, "jdk", {"version": "2"})
    ET.SubElement(jdk, "name", {"value": sdk_name})
    ET.SubElement(jdk, "type", {"value": "JavaSDK"})
    ET.SubElement(jdk, "version", {"value": "java version \"%s\"" % jdk_ver})
    ET.SubElement(jdk, "homePath", {"value": jdk_home})
    roots = ET.SubElement(jdk, "roots")
    for kind in ("annotationsPath", "classPath", "javadocPath", "sourcePath"):
        r = ET.SubElement(roots, kind)
        ET.SubElement(r, "root", {"type": "composite"})
    save(tree, tbl)
    registered.append((d, sdk_name, "added"))

for d, name, how in registered:
    print("  SDK '%s' %s in %s" % (name, how, os.path.join(d, "options", "jdk.table.xml")))

chosen = registered[0][1] if registered else sdk_name
if not registered:
    print("  no IntelliJ config dir found - relying on #JAVA_HOME instead")
    chosen = "#JAVA_HOME"

# 2) project .idea/gradle.xml: set gradleJvm ---------------------------------
gx = os.path.join(project, ".idea", "gradle.xml")
if os.path.exists(gx):
    tree = ET.parse(gx); root = tree.getroot()
else:
    root = ET.Element("project", {"version": "4"}); tree = ET.ElementTree(root)

comp = root.find("./component[@name='GradleSettings']")
if comp is None:
    comp = ET.SubElement(root, "component", {"name": "GradleSettings"})
linked = comp.find("./option[@name='linkedExternalProjectsSettings']")
if linked is None:
    linked = ET.SubElement(comp, "option", {"name": "linkedExternalProjectsSettings"})
gps = linked.find("GradleProjectSettings")
if gps is None:
    gps = ET.SubElement(linked, "GradleProjectSettings")
    ET.SubElement(gps, "option", {"name": "externalProjectPath", "value": "$PROJECT_DIR$"})

for opt in gps.findall("./option[@name='gradleJvm']"):
    gps.remove(opt)
ET.SubElement(gps, "option", {"name": "gradleJvm", "value": chosen})
save(tree, gx)
print("  gradleJvm = %s  ->  %s" % (chosen, gx))
PY

# --- make JAVA_HOME durable too (harmless belt-and-braces) -------------------
PROFILE="$HOME/.profile"
if ! grep -q "JAVA_HOME=$JDK_HOME" "$PROFILE" 2>/dev/null; then
    printf '\n# added by configure-intellij-gradle-jvm.sh\nexport JAVA_HOME=%s\n' "$JDK_HOME" >> "$PROFILE"
    log "  JAVA_HOME exported in $PROFILE (applies to new login sessions)"
fi

# --- verify from the command line -------------------------------------------
log ""
log "Verifying Gradle can run with this JDK..."
if [ -x "$PROJECT_DIR/gradlew" ]; then
    ( cd "$PROJECT_DIR" && JAVA_HOME="$JDK_HOME" ./gradlew -q --version 2>&1 | grep -E "Gradle|JVM" | sed 's/^/  /' )
    log ""
    log "Now start IntelliJ and re-sync. If it still complains, open"
    log "  Settings > Build, Execution, Deployment > Build Tools > Gradle > Gradle JVM"
    log "and confirm it shows $JDK_HOME."
else
    log "  (no gradlew in $PROJECT_DIR - skipped)"
fi
