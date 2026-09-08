# Vulnerads

A deliberately vulnerable classified ads web application for security training. It demonstrates common vulnerabilities including SQL injection, weak password hashing, command injection, XSS, XXE, CSRF/CSP misconfigurations, and LLM prompt injection. Fixed versions for each category are in the `solutions/` directory.

> **Warning:** Do not deploy this application in a production environment or expose it to the internet.

## Prerequisites

- JDK 17 or newer
- Docker (for PostgreSQL) or a local PostgreSQL 17 installation
- Gradle (wrapper included)

## Setup

### 1. Database

Start PostgreSQL via Docker:

```bash
docker-compose up -d
```

This starts a PostgreSQL 17 container with:

| Setting  | Value        |
|----------|--------------|
| Host     | localhost:5432 |
| Database | trainingsdb  |
| User     | training     |
| Password | training     |

The schema and seed data are applied automatically on first startup via Hibernate DDL and `src/main/resources/data.sql`.

To use a local PostgreSQL installation instead, create the database and user manually to match the values above, then skip the Docker step.

### 2. Photo assets

Copy the bundled sample photos to your home directory:

```bash
# Linux / macOS
cp -r assets/vulnerapp_photos ~/vulnerapp_photos

# Windows (PowerShell)
Copy-Item -Recurse assets\vulnerapp_photos "$env:USERPROFILE\vulnerapp_photos"
```

The application reads and writes uploaded photos from `~/vulnerapp_photos/`. Without this directory the sample ads will show broken images.

### 3. Ad translation (optional)

The ad detail page shows a "Translate to English" button when the interface language
is English (`?lang=en`). It sends the ad title and description to an LLM through
fal.ai, and is the basis of the prompt injection exercise.

Copy the template and paste your keyvault token:

```bash
cp src/main/resources/local.properties.example src/main/resources/local.properties
```

```properties
translation.keyvault-token=<your keyvault token>
```

`local.properties` is gitignored - never commit the token. The application uses it only
to fetch the fal.ai API key at runtime, so that key is never written to disk. Without a
token the application still starts and every other exercise works; only the translate
button fails, with a message telling you what to do.

### 4. Run

```bash
./gradlew bootRun
```

The application starts at [http://localhost:8080](http://localhost:8080).

## Default accounts

| Username | Password         | Role  |
|----------|------------------|-------|
| admin    | admin            | Admin |
| g        | g                | User  |
| herbert  | h                | User  |
| jens     | j                | User  |
| gisi     | wzPvij*,dmimj1gS | User  |
| werner   | justinbieber     | User  |
| sibylle  | shepherdess      | User  |

## Domain routing

The application is designed to be accessed at **http://vulnerads.de** (and **http://attacat.de** for the companion attack site). Both domains must resolve to `127.0.0.1` on the training machine. A helper script is provided:

```bash
sudo ./route-domains-to-localhost-dns.sh
```

This appends the following entries to `/etc/hosts`:

```
127.0.0.1  vulnerads.de
127.0.0.1  attacat.de
```

After this, [http://vulnerads.de:8080](http://vulnerads.de:8080) reaches the running application.

## HTTPS (optional)

To run over HTTPS on port 8443:

### 1. Obtain certificates

```bash
./update_certs.sh
```

This downloads valid Let's Encrypt certificates for `vulnerads.de` from the course server and places them in the expected locations.

### 2. Convert to PKCS12

```bash
./make_p12_from_pems.sh
```

This produces `src/main/resources/vulnerads.de.p12` (password: `sosec`).

### 3. Enable SSL in application.properties

Uncomment the SSL block and switch the port:

```properties
# server.port=8080
server.port=8443
server.ssl.key-store-type=PKCS12
server.ssl.key-store=classpath:vulnerads.de.p12
server.ssl.key-store-password=sosec
```

The application is then reachable at [https://vulnerads.de:8443](https://vulnerads.de:8443).

## Build

```bash
./gradlew build   # compile and package
./gradlew clean   # remove build output
```
