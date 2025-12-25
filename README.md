Multi-Tenant Cost Tracking API
Overview

This project is a backend service built with Spring Boot that tracks daily cloud costs for multiple tenants.
Each tenant can manage multiple teams, and each team can submit daily cost records.

A core design requirement of this system is strict tenant isolation — data belonging to one tenant is never accessible by another tenant. Tenant boundaries are enforced server-side and never trusted from client input.

The application is fully containerized using Docker Compose and can be started with a single command for local evaluation.

---

## Technology Stack

* Java 21
* Spring Boot
* Spring JDBC (no JPA / Hibernate)
* MySQL 8
* Maven
* Docker and Docker Compose



## Running the Application

### Prerequisites

* Docker
* Docker Compose

### Start the Application

From the project root directory:

```bash
docker compose up --build
```

This command will:

* Build the Spring Boot application
* Start a MySQL database container
* Start the backend service

The application will be available at:

```
http://localhost:8080
```

---

## API Usage & Testing

This is a backend-only application.
Endpoints can be tested using Postman or curl.

---

 1. Create Tenant (Global Endpoint)

```
POST /tenants
```

Request body:

```json
{
  "name": "Ajit Kasabe"
}
```

Response:

```json
{
  "id": "tenant-id",
  "name": "Ajit Kasabe"
}
```

The returned `id` must be provided as `X-Tenant-Id` for all tenant-scoped requests.

---

### 2. Create Team (Tenant Scoped)

```
POST /teams
```

Headers:

```
X-Tenant-Id: <TENANT_ID>
Content-Type: application/json
```

Request body:

```json
{
  "name": ""
}
```

---

### 3. Create Cost Record (Tenant Scoped)

```
POST /costs?teamId=<TEAM_ID>
```

Headers:

```
X-Tenant-Id: <TENANT_ID>
Content-Type: application/json
```

Request body:

```json
{
  "date": "2025-01-01",
  "costAmount": 123.45
}
```

---

## Tenant Isolation

* All endpoints except tenant creation require `X-Tenant-Id`
* Tenant validation is enforced for every request
* Cross-tenant access is prevented at the database and service layers
* Missing or invalid tenant IDs return **400 Bad Request**

Tenant isolation is **fully enforced by the backend**.

---

## Database

* MySQL runs inside a Docker container
* Schema is initialized automatically via `schema.sql`
* Data can be inspected using:

```bash
docker exec -it costdb mysql -u root -p
```

```sql
USE costdb;
SELECT * FROM tenants;
SELECT * FROM teams;
SELECT * FROM cost_records;
```

---

 Design Decisions

* Spring JDBC chosen over JPA for simplicity and explicit SQL control
* Header based multi-tenancy keeps the API stateless and clear
* Docker Compose ensures consistent and reproducible evaluation
* Authentication and authorization intentionally excluded (out of scope)

---

Assumptions and Trade offs

* Backend-only service (no UI)
* No authentication or user management
* Local Docker deployment only
* Focus on correctness and tenant isolation over feature completeness

---

## Stopping the Application

```bash
docker compose down
```

To remove all data and start fresh:

```bash
docker compose down -v

