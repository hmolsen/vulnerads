<#
================================================================================
 Vulnerads - portable PostgreSQL control script
================================================================================

 USAGE
     .\db.ps1 start     # start the database (do this before .\gradlew bootRun)
     .\db.ps1 stop      # shut it down
     .\db.ps1 status    # is it running?
     .\db.ps1 psql      # open an interactive SQL shell on trainingsdb

 The database does NOT auto-start with Windows. You must run `.\db.ps1 start`
 in every new session before starting the app, or Spring Boot will fail to
 connect.

================================================================================
 FIRST-TIME SETUP ON A NEW WINDOWS MACHINE
================================================================================

 The PostgreSQL binaries (pgsql\, ~700 MB) and the database cluster (pgdata\)
 are intentionally NOT in git - see .gitignore. Recreate them with the five
 steps below. Run everything from the repository root in PowerShell. No admin
 rights, no installer, and no Hyper-V/Docker required.

 ---- Step 0: prerequisite - JDK 25 -------------------------------------------

 The app needs JDK 25. gradle.properties hard-codes an absolute path:

     org.gradle.java.home=C:/Program Files/Eclipse Adoptium/jdk-25.0.2.10-hotspot

 If your JDK lives elsewhere, edit that line to match. Get JDK 25 from:
     https://adoptium.net/temurin/releases/?version=25

 ---- Step 1: download the portable PostgreSQL binaries -----------------------

 EnterpriseDB publishes PostgreSQL as a plain zip with no installer. Version
 17.9 is used here to match the postgres:17 image in docker-compose.yml.

     curl.exe -L -o "$env:TEMP\pg17.zip" `
       "https://get.enterprisedb.com/postgresql/postgresql-17.9-1-windows-x64-binaries.zip"

 Expected size: 334,313,473 bytes.
 Other versions are listed at:
     https://www.enterprisedb.com/download-postgresql-binaries
 (Pick "Windows x86-64" under "PostgreSQL Binaries", not the installer. The URL
 pattern is postgresql-<major>.<minor>-<build>-windows-x64-binaries.zip; a
 version that does not exist returns HTTP 403.)

 ---- Step 2: extract into the repository root --------------------------------

 The zip already contains a top-level pgsql\ folder, so extract to the repo
 root and you get .\pgsql\. Use Windows' bundled tar.exe - Expand-Archive is
 very slow on a file this size.

     & "$env:SystemRoot\System32\tar.exe" -xf "$env:TEMP\pg17.zip" -C .

 Verify:
     .\pgsql\bin\postgres.exe --version     # -> postgres (PostgreSQL) 17.9

 ---- Step 3: create the database cluster -------------------------------------

 IMPORTANT - the locale flag is not optional. On Windows, initdb rejects
 --encoding=UTF8 combined with a Windows locale such as German_Germany.1252
 ("encoding UTF8 does not match locale"). Use --locale=C, which keeps UTF-8
 storage and only changes text sort order to byte order. UTF-8 is required:
 src\main\resources\data.sql contains German umlauts (Lubeck, Schafer, Tuv)
 which arrive as mojibake under any other encoding, and because
 spring.jpa.hibernate.ddl-auto=create-drop reloads data.sql on every app start,
 you would see it constantly.

     $pw = "$env:TEMP\pgpw.txt"
     Set-Content -Path $pw -Value "training" -NoNewline -Encoding ascii
     .\pgsql\bin\initdb.exe -D .\pgdata -U training -A scram-sha-256 `
         --pwfile=$pw --encoding=UTF8 --locale=C
     Remove-Item $pw -Force

 The user name, password and database name below are NOT arbitrary - they must
 match src\main\resources\application.properties, which is left untouched:
     spring.datasource.url=jdbc:postgresql://localhost/trainingsdb
     spring.datasource.username=training
     spring.datasource.password=training

 ---- Step 4: start the server and create the database ------------------------

     .\db.ps1 start
     $env:PGPASSWORD = "training"
     .\pgsql\bin\createdb.exe -h localhost -p 5432 -U training -O training -E UTF8 trainingsdb
     $env:PGPASSWORD = $null

 Port 5432 must be free. If a native PostgreSQL or another service already
 listens there, change the port in BOTH this script and the JDBC URL in
 application.properties.

 ---- Step 5: run the app -----------------------------------------------------

     .\gradlew bootRun          # -> http://localhost:8080

 Logins: admin/admin, g/g, herbert/h, jens/j

 ---- Uninstall ---------------------------------------------------------------

     .\db.ps1 stop
     Remove-Item -Recurse -Force .\pgsql, .\pgdata, .\pg.log

 Nothing is installed system-wide and no Windows service is registered, so
 deleting those folders removes every trace.

================================================================================
#>

param(
    [Parameter(Position = 0)]
    [ValidateSet('start', 'stop', 'status', 'psql')]
    [string]$Command = 'status'
)

$bin  = Join-Path $PSScriptRoot 'pgsql\bin'
$data = Join-Path $PSScriptRoot 'pgdata'
$log  = Join-Path $PSScriptRoot 'pg.log'

if (-not (Test-Path $bin)) {
    Write-Error "pgsql\bin not found. See FIRST-TIME SETUP in the header of this script."
    exit 1
}
if (-not (Test-Path $data)) {
    Write-Error "pgdata not found. Run the initdb step - see FIRST-TIME SETUP in the header of this script."
    exit 1
}

switch ($Command) {
    'start'  { & "$bin\pg_ctl.exe" -D $data -l $log -o "-p 5432" start }
    'stop'   { & "$bin\pg_ctl.exe" -D $data stop }
    'status' { & "$bin\pg_ctl.exe" -D $data status }
    'psql'   {
        $env:PGPASSWORD = 'training'
        try { & "$bin\psql.exe" -h localhost -p 5432 -U training -d trainingsdb }
        finally { $env:PGPASSWORD = $null }
    }
}
