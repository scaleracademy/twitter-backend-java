# Moo: Twitter Clone Backend

[![Master CI](https://github.com/scaleracademy/twitter-backend-java/actions/workflows/master-ci.yml/badge.svg)](https://github.com/scaleracademy/twitter-backend-java/actions/workflows/master-ci.yml)
[![PR Checker](https://github.com/scaleracademy/twitter-backend-java/actions/workflows/pr-checker.yml/badge.svg)](https://github.com/scaleracademy/twitter-backend-java/actions/workflows/pr-checker.yml)
[![License: AGPL v3](https://img.shields.io/badge/License-AGPL%20v3-blue.svg)](https://www.gnu.org/licenses/agpl-3.0)

A robust, high-performance Twitter clone backend built with the latest
industry standards.

## Modern Tech Stack

- **Java 21** (Microsoft Build of OpenJDK)
- **Spring Boot 4.0** (Spring Framework 7.0)
- **Jakarta EE 11** (Jakarta Persistence & Servlet APIs)
- **Virtual Threads (Project Loom)** enabled for massive concurrency.
- **JWT-based Security** via Spring Security 7.0.
- **Spring Data JPA** with MySQL/H2 support.
- **OpenTelemetry** for native observability.
- **Springdoc OpenAPI 3** for Swagger documentation.

## Getting Started

### Prerequisites

- JDK 21+
- Maven 3.9+
- Docker & Docker Compose (optional)

### Quick Start

Choose the setup that fits your workflow:

#### Option A: Zero-Installation (Full Stack)

Ideal for testing or a quick look. Runs everything in Docker.

1. `cp .env.example .env`
2. `docker compose up -d`
3. API: `http://localhost:8082` | DB Admin: `http://localhost:8083`

#### Option B: Native Development (Dependencies Only)

Ideal for coding. Runs DB in Docker, App in your IDE/CLI.

1. `cp .env.example .env`
2. `docker compose -f docker-compose.dev.yml up -d`
3. Run the **"Moo API"** configuration in IntelliJ IDEA, or use:
   `./mvnw spring-boot:run -Dspring-boot.run.profiles=dev`
4. API: `http://localhost:8080` (Standard) | DB Admin: `http://localhost:8083`

## API Documentation

Access the Interactive Swagger UI at:
👉 `http://localhost:8082/swagger-ui/index.html`

### Key Endpoints

- **POST `/authenticate`**: Login and receive a JWT token.
- **GET `/users`**: User management and social graph.
- **GET `/posts`**: Scalable paginated feed access.

## Architecture & Best Practices

- **Scalable Pagination**: All list endpoints use `Pageable` to prevent memory
  exhaustion.
- **Distroless Containers**: Docker images are built using Google's
  **Distroless** images for minimal attack surface and maximum security.
- **Multi-Arch Support**: Official images are published for both `amd64` and
  `arm64` architectures.
- **Zero-Qualified Names**: The codebase strictly uses imports for readability
  and cleaner syntax.
- **Null Safety**: Integrated **JSpecify** annotations across the service
  layer.

## CI/CD Pipeline

Our pipeline is optimized for robust speed and reliability:

- **PR Checker**: Runs cross-platform linting (Spotless) and a Docker smoke
  build on every PR.
- **Master CI**: Performs artifact archival, CodeQL security scanning, and
  multi-arch image distribution to GHCR and DockerHub.

## Contributing

We welcome contributions! Please see our [Contributing Guidelines](CONTRIBUTING.md)
for details on:

- Coding standards (Spotless, JSpecify, etc.)
- Development environment setup
- Pull request process

## License

This project is licensed under the **GNU Affero General Public License v3.0**.
