# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Purpose

**Vulnerads** is a deliberately vulnerable classified ads web application (Spring Boot + PostgreSQL) designed as a security training tool. All vulnerabilities are intentional. The `/solutions/` directory contains fixed versions for each category.

## Build & Run Commands

```bash
# Build
./gradlew build

# Run (requires PostgreSQL — start it first)
docker-compose up -d          # start PostgreSQL
./gradlew bootRun             # app available at http://localhost:8080

# Clean
./gradlew clean
```

**Prerequisites**: JDK 25 (set in `gradle.properties`). No test suite exists — this is a training application.

## Architecture

**Stack**: Spring Boot 3.4.4 · Jakarta EE 9+ · JSP views · PostgreSQL 17 · Gradle

**Request flow**: HTTP → Controller → Service → Repository (JPA + raw JDBC) → Entity → JSP view

**Key packages** (`src/main/java/de/cqrity/vulnerapp/`):

| Package | Role |
|---|---|
| `config/` | Spring Security config, MVC config, MD5 password encoder, AES database encryptor |
| `controller/` | HTTP handlers for ads, users, admin, images, login |
| `service/` | Business logic — notably `ClassifiedAdService` (SQL injection) and `ImageService` |
| `repository/` | JPA repositories; `ClassifiedAdRepository` has raw JDBC examples |
| `domain/` | JPA entities (`User`, `ClassifiedAd`, `Image`) + DTOs |
| `tfa/` | TOTP-based two-factor authentication provider and TOTP verifier |
| `util/` | `VirusScanner` (command injection), `WhitelistHtmlSanitizerTag` (JSP tag) |
| `xml/` | JAXB root element for ad import (XXE vulnerability surface) |

**Views**: `src/main/webapp/WEB-INF/jsp/` — JSP templates rendered by `InternalResourceViewResolver`.

**Database**: Initialized on every startup via `src/main/resources/data.sql` + Hibernate DDL (`spring.jpa.hibernate.ddl-auto=update`). Default users: `admin/admin`, `g/g`, `herbert/h`, `jens/j`.

## Intentional Vulnerabilities

| Category | Location |
|---|---|
| SQL Injection | `ClassifiedAdService.java` — raw string concatenation in search query |
| Weak password hashing | `Md5PasswordEncoder.java` — MD5 instead of BCrypt |
| Command injection | `VirusScanner.java` — username concatenated into shell command |
| XXE | `ClassifiedAdService.java` — JAXB unmarshaller without XXE protection |
| XSS | `ClassifiedAd.java` — raw HTML in description field |
| CSRF/CSP/CORS | `WebMvcSecurityConfig.java` / `WebMvcConfig.java` — protections disabled |

When proposing fixes, check the corresponding directory in `/solutions/` first to see the intended remediation approach.

## Configuration Notes

- **Database**: PostgreSQL by default; HSQLDB alternative is commented out in `build.gradle`
- **File uploads**: Max 12 MB, stored in `${user.home}/vulnerapp_photos/`
- **Locale**: German (`de_DE`) by default; switchable via `?lang=en`
- **SSL**: Disabled by default; `server.ssl.*` properties in `application.properties` can enable it
- **CI**: CodeQL analysis runs on push/PR to master (`.github/workflows/codeql-analysis.yml`)