# GEMINI.md - Twitter Backend Java

## Project Overview
This repository contains the backend for a Twitter clone application (Moo), developed using **Java 21**, **Spring Boot 2.7.17**, and **Spring Data JPA**. It provides RESTful APIs for user management, posting, hashtags, and social interactions (follows, likes).

### Core Technologies
- **Language:** Java 21
- **Framework:** Spring Boot (Web, JPA, Actuator, DevTools)
- **Database:** MySQL (Production/Dev), H2 (Testing)
- **Build System:** Maven
- **Infrastructure:** Docker & Docker Compose
- **Documentation:** Swagger/Springfox (v3.0.0)
- **Formatting:** Spotless with Google Java Format
- **Monitoring:** Actuator & Jacoco for test coverage

## Architecture & Conventions
The project follows a standard layered Spring Boot architecture:
- **Controllers (`xyz.subho.clone.twitter.controller`):** REST API endpoints.
- **Services (`xyz.subho.clone.twitter.service`):** Business logic layer (Interface + Implementation pattern).
- **Repositories (`xyz.subho.clone.twitter.repository`):** Spring Data JPA interfaces.
- **Entities (`xyz.subho.clone.twitter.entity`):** JPA models for database mapping.
- **Models (`xyz.subho.clone.twitter.model`):** Data Transfer Objects (DTOs) for API communication.
- **Mappers (`xyz.subho.clone.twitter.utility`):** Converters between Entities and Models using `BeanUtils.copyProperties`.

### Key Coding Practices
- **Lombok:** Used extensively for reducing boilerplate (e.g., `@Data`, `@Slf4j`, `@Autowired` on private fields).
- **Exception Handling:** Centralized through `ControllerExceptionHandler` and `ApplicationErrorController`.
- **Profiles:** `dev` (Local MySQL), `test` (H2 database), and `prod`.

## Building and Running

### Prerequisites
- Java 21
- Maven 3.x
- Docker & Docker Compose (optional)

### Key Commands
- **Install dependencies:** `mvn install`
- **Run locally (Spring Boot):** `mvn spring-boot:run`
- **Run tests:** `mvn test`
- **Run with Docker Compose:** `docker-compose up -d`
- **Apply formatting (Spotless):** `mvn spotless:apply`
- **Generate coverage report:** `mvn jacoco:report`

### Configuration
1. Copy `.env.example` to `.env`.
2. Set the active profile in `application.properties`: `spring.profiles.active=dev`.
3. Configure database credentials in the `.env` or profile-specific property files (e.g., `application-dev.properties`).

## Development Guidelines
- **Adding new features:** Follow the Controller -> Service -> Repository flow. Ensure every entity has a corresponding Model and Mapper.
- **Testing:** Add JUnit tests in `src/test/java`. The `test` profile uses an in-memory H2 database.
- **Formatting:** Ensure `mvn spotless:apply` is run before committing code to maintain consistent style (Google Java Format).
- **Documentation:** Access Swagger UI at `http://localhost:8080/swagger-ui/` (when running).
