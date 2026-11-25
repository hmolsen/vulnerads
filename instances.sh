#!/usr/bin/env bash
# Run several isolated Vulnerads instances, each with its own database, upload
# folder, port and public Cloudflare quick tunnel. Meant for trainings where
# participants scan the app (e.g. with ZAP) without disturbing each other.
#
#   ./instances.sh start          build once with Gradle, start all instances + tunnels
#   ./instances.sh stop           stop all instances and tunnels
#   ./instances.sh status         show ports, PIDs, memory and public URLs
#   ./instances.sh reset <n>      restart instance n with a fresh database (URL stays the same)
#
# Settings (environment variables):
#   COUNT=12                 number of instances
#   BASE_PORT=8080           instance n listens on BASE_PORT+n (8081..8092)
#   HEAP=256m                max Java heap per instance
#   POOL_SIZE=4              DB connections per instance (PostgreSQL allows 100 in total)
#   TOMCAT_THREADS=40        max request threads per instance (default 200 wastes memory)
#   ENABLE_TRANSLATION=0     1 = keep the fal.ai translation feature (costs money per request!)
#   CLOUDFLARED=~/cloudflared
#   JAVA=java                JDK 17+
#   STATE_DIR=~/vulnerads-instances

set -euo pipefail

COUNT=${COUNT:-12}
BASE_PORT=${BASE_PORT:-8080}
HEAP=${HEAP:-256m}
POOL_SIZE=${POOL_SIZE:-4}
TOMCAT_THREADS=${TOMCAT_THREADS:-40}
ENABLE_TRANSLATION=${ENABLE_TRANSLATION:-0}
CLOUDFLARED=${CLOUDFLARED:-$HOME/cloudflared}
JAVA=${JAVA:-java}
STATE_DIR=${STATE_DIR:-$HOME/vulnerads-instances}

PROJECT_DIR=$(cd "$(dirname "$0")" && pwd)
JAR="$PROJECT_DIR/build/libs/vulnerapp.jar"
SEED_PHOTOS="$HOME/vulnerapp_photos"

DB_HOST=localhost
DB_USER=training
DB_PASS=training

log() { printf '\033[1;34m==>\033[0m %s\n' "$*"; }
warn() { printf '\033[1;33m!!\033[0m %s\n' "$*" >&2; }

port_of() { echo $((BASE_PORT + $1)); }
dir_of() { echo "$STATE_DIR/i$1"; }
db_of() { echo "vulnerads_$1"; }

psql_admin() { PGPASSWORD=$DB_PASS psql -h "$DB_HOST" -U "$DB_USER" -d postgres -qAtc "$1"; }

is_running() { [[ -f $1 ]] && kill -0 "$(cat "$1")" 2>/dev/null; }

stop_pidfile() {
    local pidfile=$1
    if is_running "$pidfile"; then
        local pid; pid=$(cat "$pidfile")
        kill "$pid" 2>/dev/null || true
        for _ in $(seq 20); do kill -0 "$pid" 2>/dev/null || break; sleep 0.5; done
        kill -9 "$pid" 2>/dev/null || true
    fi
    rm -f "$pidfile"
}

build() {
    log "Building jar with Gradle"
    (cd "$PROJECT_DIR" && ./gradlew --no-daemon -q bootJar)
}

# Fresh database + fresh upload folder for instance n.
prepare_instance() {
    local n=$1 dir db
    dir=$(dir_of "$n"); db=$(db_of "$n")
    mkdir -p "$dir/home"
    rm -rf "$dir/home/vulnerapp_photos"
    if [[ -d $SEED_PHOTOS ]]; then
        cp -r "$SEED_PHOTOS" "$dir/home/vulnerapp_photos"
    else
        mkdir -p "$dir/home/vulnerapp_photos"
    fi
    psql_admin "DROP DATABASE IF EXISTS $db WITH (FORCE)"
    psql_admin "CREATE DATABASE $db OWNER $DB_USER"
}

start_app() {
    local n=$1 dir port
    dir=$(dir_of "$n"); port=$(port_of "$n")
    if is_running "$dir/app.pid"; then warn "instance $n already running"; return 1; fi
    if ss -ltn "sport = :$port" | grep -q LISTEN; then
        warn "port $port is already in use - skipping instance $n"; return 1
    fi

    local extra=()
    [[ $ENABLE_TRANSLATION == 1 ]] || extra+=(--translation.keyvault-token=)

    # setsid: own session, so the instances survive the terminal that started them.
    # Must run from the project dir: Spring Boot serves the JSPs and antivir.sh
    # from src/main/webapp relative to the working directory.
    # (cd first, then background only the java command, so $! is the JVM itself.)
    (cd "$PROJECT_DIR"; setsid nohup "$JAVA" -Xmx"$HEAP" -Xss512k -XX:+UseSerialGC \
        -Duser.home="$dir/home" \
        -jar "$JAR" \
        --server.port="$port" \
        --spring.datasource.url="jdbc:postgresql://$DB_HOST/$(db_of "$n")" \
        --spring.datasource.hikari.maximum-pool-size="$POOL_SIZE" \
        --spring.datasource.hikari.minimum-idle=1 \
        --server.tomcat.threads.max="$TOMCAT_THREADS" \
        "${extra[@]}" \
        >"$dir/app.log" 2>&1 & echo $! >"$dir/app.pid")
}

wait_app() {
    local n=$1 port dir
    port=$(port_of "$n"); dir=$(dir_of "$n")
    for _ in $(seq 120); do
        if curl -s -o /dev/null "http://localhost:$port/login"; then return 0; fi
        is_running "$dir/app.pid" || { warn "instance $n died - see $dir/app.log"; return 1; }
        sleep 1
    done
    warn "instance $n not ready after 120s - see $dir/app.log"; return 1
}

start_tunnel() {
    local n=$1 dir port
    dir=$(dir_of "$n"); port=$(port_of "$n")
    if is_running "$dir/tunnel.pid"; then return; fi
    rm -f "$dir/url"
    setsid nohup "$CLOUDFLARED" tunnel --no-autoupdate --url "http://localhost:$port" \
        >"$dir/tunnel.log" 2>&1 & echo $! >"$dir/tunnel.pid"
}

wait_tunnel() {
    local n=$1 dir url=""
    dir=$(dir_of "$n")
    for _ in $(seq 60); do
        url=$(grep -o 'https://[a-z0-9-]*\.trycloudflare\.com' "$dir/tunnel.log" 2>/dev/null | head -1 || true)
        [[ -n $url ]] && { echo "$url" >"$dir/url"; return 0; }
        is_running "$dir/tunnel.pid" || break
        sleep 1
    done
    warn "no tunnel URL for instance $n - see $dir/tunnel.log"; return 1
}

print_table() {
    printf '\n%-4s %-6s %-9s %-9s %s\n' "#" "PORT" "APP" "MEM" "PUBLIC URL"
    for n in $(seq "$COUNT"); do
        local dir state mem="-" url="-"
        dir=$(dir_of "$n")
        if is_running "$dir/app.pid"; then
            state=up
            mem="$(( $(ps -o rss= -p "$(cat "$dir/app.pid")") / 1024 ))MB"
        else
            state=down
        fi
        [[ -f $dir/url ]] && is_running "$dir/tunnel.pid" && url=$(cat "$dir/url")
        printf '%-4s %-6s %-9s %-9s %s\n' "$n" "$(port_of "$n")" "$state" "$mem" "$url"
    done
    echo
    free -h | awk 'NR==2 {print "Memory: " $3 " used, " $7 " available of " $2}'
}

cmd_start() {
    [[ -x $CLOUDFLARED ]] || { warn "cloudflared not found at $CLOUDFLARED"; exit 1; }
    "$JAVA" -version >/dev/null 2>&1 || { warn "java not found ($JAVA)"; exit 1; }
    psql_admin "SELECT 1" >/dev/null || { warn "cannot connect to PostgreSQL as $DB_USER"; exit 1; }
    [[ $ENABLE_TRANSLATION == 1 ]] && warn "translation ENABLED - every scan request to /ad/*/translate costs money"
    local max_conn
    max_conn=$(psql_admin "SHOW max_connections")
    if (( COUNT * POOL_SIZE + 10 > max_conn )); then
        warn "$COUNT instances x $POOL_SIZE connections exceeds PostgreSQL max_connections=$max_conn - lower POOL_SIZE"
        exit 1
    fi

    build
    mkdir -p "$STATE_DIR"

    # Start the apps one after another: starting many Spring Boot apps at once
    # on 4 cores just makes all of them slow.
    for n in $(seq "$COUNT"); do
        if is_running "$(dir_of "$n")/app.pid"; then warn "instance $n already running"; continue; fi
        log "Starting instance $n on port $(port_of "$n")"
        prepare_instance "$n"
        start_app "$n" && wait_app "$n" || true
    done

    for n in $(seq "$COUNT"); do
        log "Opening tunnel for instance $n"
        start_tunnel "$n"
        sleep 2   # be gentle with Cloudflare's quick tunnel rate limit
    done
    for n in $(seq "$COUNT"); do wait_tunnel "$n" || true; done

    print_table
    for n in $(seq "$COUNT"); do
        [[ -f $(dir_of "$n")/url ]] && echo "Instance $n: $(cat "$(dir_of "$n")/url")"
    done >"$STATE_DIR/urls.txt"
    log "URLs saved to $STATE_DIR/urls.txt"
}

cmd_stop() {
    # Stop every instance that exists, even if COUNT was lowered since start.
    local dir
    for dir in "$STATE_DIR"/i*; do
        [[ -d $dir ]] || continue
        log "Stopping instance ${dir##*/i}"
        stop_pidfile "$dir/tunnel.pid"
        stop_pidfile "$dir/app.pid"
        rm -f "$dir/url"
    done
}

cmd_reset() {
    local n=${1:?usage: $0 reset <n>}
    [[ $n =~ ^[0-9]+$ ]] && (( n >= 1 && n <= COUNT )) || { warn "instance must be 1..$COUNT"; exit 1; }
    log "Resetting instance $n (database and uploads; tunnel URL is kept)"
    stop_pidfile "$(dir_of "$n")/app.pid"
    prepare_instance "$n"
    start_app "$n" && wait_app "$n" && log "Instance $n is back up"
}

case ${1:-} in
    start)  cmd_start ;;
    stop)   cmd_stop ;;
    status) print_table ;;
    reset)  cmd_reset "${2:-}" ;;
    *)      sed -n '2,20p' "$0" | sed 's/^# \{0,1\}//'; exit 1 ;;
esac
