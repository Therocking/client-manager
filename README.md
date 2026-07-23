# User Management App

Full-stack app to manage users and their addresses.

## Tech stack

| Layer | Technology |
|---|---|
| Backend | Java 25, Spring Boot 3, Maven |
| Database | PostgreSQL 16 + Flyway migrations |
| API docs | Swagger UI (springdoc-openapi) |
| Frontend | SvelteKit 2 + TypeScript (Vite), pnpm |
| Tests | Testcontainers (real PostgreSQL) |
| Infra | Docker, Docker Compose |
| CI | GitHub Actions |

## Architecture

The backend follows **Clean Architecture** with a code-only **CQRS** split:

```
domain/        → entities (User, Address) + repository interfaces
application/
  command/     → write operations (@Transactional)
  query/       → read operations (readOnly)
infrastructure/
  persistence/ → Spring Data JPA adapters
  web/         → REST controllers, DTOs, exception handler
```

Controllers call either a `*CommandService` or a `*QueryService`, never both in one handler.

## API

Base URL: `http://localhost:8080`  
Swagger UI: `http://localhost:8080/swagger-ui.html`

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/v1/users` | List all users |
| POST | `/api/v1/users` | Create a user |
| GET | `/api/v1/users/{id}` | Get user with addresses |
| PUT | `/api/v1/users/{id}` | Update a user |
| DELETE | `/api/v1/users/{id}` | Delete a user (cascades addresses) |
| POST | `/api/v1/users/{id}/addresses` | Add an address |
| PUT | `/api/v1/users/{id}/addresses/{addrId}` | Update an address |
| DELETE | `/api/v1/users/{id}/addresses/{addrId}` | Delete an address |

## Running locally

### With Docker Compose

```bash
docker compose up --build
```

- Frontend: http://localhost
- Backend / Swagger: http://localhost:8080/swagger-ui.html

### Backend only (needs a running PostgreSQL)

```bash
cd backend
mvn spring-boot:run
```

### Frontend only

Run on Linux or WSL — pnpm requires symlink support not available on Windows NTFS mounts.

```bash
cd frontend
pnpm install
pnpm dev
```

## Running tests

Integration tests spin up a real PostgreSQL container via Testcontainers (Docker required).

```bash
cd backend
mvn verify
```

## Frontend pages

| Route | View |
|---|---|
| `/` | User list with Edit and Delete per row |
| `/users/new` | Create user |
| `/users/:id` | User detail with address list (Edit/Delete per address) |
| `/users/:id/edit` | Edit user |
| `/users/:id/addresses/new` | Add address |
| `/users/:id/addresses/:addrId/edit` | Edit address |
