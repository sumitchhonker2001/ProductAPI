# Zest India IT - Product REST API

Complete Java 17 / Spring Boot implementation for the supplied backend assignment. The assignment asks for product CRUD, `/api/v1/` versioning, pagination, standardized errors, JWT + refresh-token rotation, role-based authorization, Jakarta validation, indexing, async-capable architecture, CORS/HTTPS considerations, JUnit/Mockito + H2 tests, OpenAPI, Docker and Docker Compose.

## Architecture
`Controller -> Service -> Repository -> PostgreSQL`

- DTOs isolate API contracts from JPA entities.
- Service layer owns business rules and audit fields.
- Spring Security is stateless; JWT access tokens are short-lived.
- Refresh tokens are persisted and rotated: a refresh token is revoked when consumed and a new one is issued.
- `ADMIN` can create/update/delete products; `USER` and `ADMIN` can read products.
- Product and item foreign-key columns are indexed.
- Global exception handler returns a consistent JSON error structure.

## Run with Docker
```bash
mvn clean package -DskipTests
# then
 docker compose up --build
```
API: `http://localhost:8080`
Swagger UI: `http://localhost:8080/swagger-ui.html`

## Local run
Requires Java 17+ and Maven. Start PostgreSQL and set `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`, then:
```bash
mvn spring-boot:run
```

## Demo users
- `admin / Admin@123` -> ADMIN
- `user / User@123` -> USER
Change/remove these seeded credentials for any real deployment.

## Authentication
`POST /api/v1/auth/login`
```json
{"username":"admin","password":"Admin@123"}
```
Use returned access token as `Authorization: Bearer <token>`.

`POST /api/v1/auth/refresh`
```json
{"refreshToken":"<refresh-token>"}
```

## Product endpoints
- GET `/api/v1/products?page=0&size=10`
- GET `/api/v1/products/{id}`
- POST `/api/v1/products`
- PUT `/api/v1/products/{id}`
- DELETE `/api/v1/products/{id}`
- GET `/api/v1/products/{id}/items`

Create/update body:
```json
{"productName":"Laptop"}
```

## HTTPS / CORS
TLS termination is intended at the reverse proxy/load balancer in deployment. CORS is configurable with `APP_CORS_ORIGIN`. For production, enforce HTTPS at the edge and use a strong secret through environment/secret management rather than committing it.

## Tests
Tests use JUnit 5, Mockito, Spring Security Test and H2. Run:
```bash
mvn test
```

## Submission
The supplied assignment explicitly says to create a **public GitHub repository**, push the complete source, include README/Docker files, and submit **only the GitHub repository URL**; it also explicitly says not to submit a ZIP. This ZIP is provided as a convenient local copy, but GitHub is the expected final submission method.
