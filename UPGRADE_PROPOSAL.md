# Spring Boot 4.0 Upgrade & Enterprise Readiness Proposal
**Target Audience:** New Graduate Software Engineer / Spring Boot Intern
**Project:** Twitter Backend Java

## 1. Executive Summary
Welcome to the cutting edge! This document outlines the technical plan to leapfrog our application from Spring Boot 2.7.x all the way to **Spring Boot 4.0** (Released Nov 2025). This upgrade aligns us with **Spring Framework 7.0**, **Jakarta EE 11**, and provides first-class support for our **Java 21** environment.

Spring Boot 4 is a generational shift focusing on modularity, enhanced observability via OpenTelemetry, and native support for modern API patterns like built-in versioning.

---

## 2. Phase 1: Dependency & Build Upgrades (`pom.xml`)
We are moving from a monolithic dependencies model to a more modular one.

**Actions Required:**
1. **Spring Boot Parent:** Update `<version>2.7.17</version>` to `<version>4.0.0</version>`.
2. **Modular Starters:** Spring Boot 4 uses smaller, refactored starters. You may need to add specific modules that were previously bundled.
3. **Swagger/OpenAPI Migration:**
   - **Why:** `springfox` is long dead. Spring Boot 4 officially recommends `springdoc-openapi-starter-webmvc-ui` (version `3.x.x` for Spring Boot 4).
   - **Action:** Remove `springfox-boot-starter`. Add `springdoc-openapi-starter-webmvc-ui`.
4. **OpenTelemetry for Observability:**
   - **Action:** Add `spring-boot-starter-opentelemetry`. This replaces the fragmented Micrometer/Zipkin setup with a unified standard for enterprise-grade tracing and metrics.

**Potential Pitfall:** Spring Boot 4.0 removed many legacy classes that were deprecated in 3.x. Ensure you have no "deprecated" warnings before making the final jump to 4.0.

---

## 3. Phase 2: Jakarta EE 11 Namespace Migration
Spring Boot 4 aligns with **Jakarta EE 11**.

**Actions Required:**
1. **Namespace Swap:** Replace all `javax.*` imports with `jakarta.*`.
   - *Target:* `jakarta.persistence.*` for entities, `jakarta.servlet.*` for controllers.
   - *Specifics:* Ensure `JPA 3.2` and `Servlet 6.1` features are utilized where possible for performance gains.

---

## 4. Phase 3: Modernizing with Spring Boot 4 Features
This is where we go beyond just "upgrading" and start "modernizing".

**Actions Required:**
1. **Built-in API Versioning:**
   - **Why:** Enterprise apps must version their APIs (v1, v2).
   - **Action:** Use the new `spring.mvc.apiversion.*` properties to enable native versioning. Instead of manual path prefixing, we will use the framework's native support.
2. **Declarative HTTP Clients:**
   - **Why:** The current `RestTemplate` usage is outdated.
   - **Action:** Define an interface with `@HttpExchange` for internal or external service calls. Spring Boot 4 will automatically generate the client.
3. **JSpecify for Null Safety:**
   - **Action:** Adopt `JSpecify` annotations across our Models and Services to eliminate `NullPointerExceptions` at compile time.

---

## 5. Phase 4: Application Properties & Data Layer
Spring Boot 4 brings **Hibernate 7.x** (or latest 6.x optimized for Jakarta 11).

**Actions Required:**
1. **Dialect-Free Configuration:** Remove all `spring.jpa.properties.hibernate.dialect` entries. Hibernate will auto-detect the optimal SQL for your database.
2. **Virtual Threads (Project Loom):**
   - **Action:** Set `spring.threads.virtual.enabled=true`.
   - **Why:** This allows the app to handle millions of concurrent requests (like a real Twitter) using lightweight virtual threads instead of heavy OS threads.

---

## 6. Phase 5: Enterprise "De-drifting"
Fixing the shortcuts from the clone project.

### A. Authentication & Security (Jakarta 11 Style)
- **Action:** Implement a state-of-the-art JWT security filter using Spring Security 7.
- **Action:** Fix the `UUID.randomUUID()` mock in `UserController.java` by extracting the authenticated user from the Security Context.

### B. Scalable Pagination
- **Action:** Every `List` return in Controllers must be converted to `Page<T>` or `Slice<T>`. Returning full lists is a "Junior" mistake that crashes production systems.

### C. Bean Validation 3.1
- **Action:** Use `@NotBlank`, `@Size`, and the new `@Range` annotations on DTOs to ensure data integrity before it hits the service layer.

---

## 7. Step-by-Step Execution Checklist for You

1. [ ] **Branch Out:** `git checkout -b feature/spring-boot-4-upgrade`
2. [ ] **Pre-Upgrade (Recommended):** Upgrade to 3.5 first to clear deprecations.
3. [ ] **Target 4.0:** Update `pom.xml` to Spring Boot `4.0.0`.
4. [ ] **Jakarta Migration:** Bulk replace `javax` -> `jakarta`.
5. [ ] **Enable Virtual Threads:** Add `spring.threads.virtual.enabled=true` to `application.properties`.
6. [ ] **OpenTelemetry:** Add the OTEL starter for observability.
7. [ ] **Refactor Controllers:** Implement native API versioning.
8. [ ] **Fix Mocks:** Replace `UUID.randomUUID()` with actual security-authenticated user IDs.
9. [ ] **Pagination:** Refactor `getFollowers` and `getFollowings` to use `Pageable`.
10. [ ] **Verify:** Run `mvn clean install` and check the Swagger UI at `/swagger-ui/index.html`.

This is a massive upgrade. You are effectively taking a 2021-era project and catapulting it into 2026 standards.
