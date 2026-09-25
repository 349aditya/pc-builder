# PC Builder

A microservices-based PC building platform for creating, customizing,
and validating PC configurations.

## Tech Stack

- Java 21
- Spring Boot 4.1.0
- Spring Cloud 2025.1.2
- Spring Cloud Gateway
- Spring Security
- Apache Kafka
- Redis
- MySQL
- Maven

## Services

- Catalog Service (`8080`) — component catalog, search, filtering, prices, and stock
- Compatibility Service (`8081`) — rule-based component compatibility and wattage estimation
- Build Service (`8082`) — saved builds, total pricing, and compatibility coordination

## Main API

### Catalog

```text
GET  /api/components
GET  /api/components/{id}
POST /api/components/bulk
```

`GET /api/components` supports `category`, `brand`, `keyword`, `minPrice`,
`maxPrice`, `socketType`, `ramType`, `minWattage`, `maxLengthMm`, `page`,
`size`, and `sort` query parameters.

Catalog management endpoints:

```text
POST   /api/components
PUT    /api/components/{id}
PATCH  /api/components/{id}/stock
DELETE /api/components/{id}
```

### Compatibility

```text
POST /api/compatibility/check
```

The request can contain a partial build. The service validates component
categories and evaluates socket, RAM, case clearance, storage, fan, and power
rules.
Incompatible results also include actionable suggestions for selecting a
replacement part.

### Builds

```text
POST   /api/builds
GET    /api/builds
GET    /api/builds/{id}
PUT    /api/builds/{id}
POST   /api/builds/{id}/validate
DELETE /api/builds/{id}
```

Creating or updating a build resolves current catalog prices, validates that
each ID belongs to the expected category, calculates the total price, and runs
the compatibility service.

## Local Configuration

Create two MariaDB databases:

```sql
CREATE DATABASE pcbuilder_catalog;
CREATE DATABASE pcbuilder_builds;
```

The services use these environment variables when provided:

```text
DB_URL, DB_USERNAME, DB_PASSWORD
BUILD_DB_URL, BUILD_DB_USERNAME, BUILD_DB_PASSWORD
CATALOG_SERVICE_URL, COMPATIBILITY_SERVICE_URL
```

Start the services in this order: catalog, compatibility, then build.

## Project Status

Core catalog, compatibility, and saved-build features are implemented. API
gateway, identity, and frontend work remain for later phases.
