<!--
  Twitter Backend - Moo: Twitter Clone Application Backend by Scaler
  Copyright © 2021-2026 Subhrodip Mohanta (hello@subho.xyz)

  This program is free software: you can redistribute it and/or modify
  it under the terms of the GNU Affero General Public License as published by
  the Free Software Foundation, either version 3 of the License, or
  (at your option) any later version.

  This program is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU Affero General Public License for more details.

  You should have received a copy of the GNU Affero General Public License
  along with this program.  If not, see <http://www.gnu.org/licenses/>.
-->

# Moo: Robust Evolution Plan

This document outlines a deep-dive analysis of the current Moo: Twitter Clone
Backend and proposes a series of advanced improvements to transition the
project into a world-class, production-ready application.

---

## 1. Data Architecture & Scalability

### A. Graph Optimization (Followers/Following)

* **Current Issue:** Users are stored using `@ElementCollection` with a
  `Map<UUID, Date>`. This results in a join table that lacks primary keys and is
  inefficient for large social graphs.

* **Proposal:** Refactor to a dedicated `Follow` entity or a proper
  `@ManyToMany` relationship with a join table.

* **Impact:** Enables efficient querying (e.g., "Find mutual followers"),
  prevents duplicate entries, and supports better indexing for large-scale data.

### B. Audit Trail Integration

* **Current Issue:** Auditing is partially manual (e.g., `@CreationTimestamp`).

* **Proposal:** Enable **Spring Data JPA Auditing**. Implement an `Auditable`
  base class with `@CreatedBy`, `@LastModifiedBy`, `@CreatedDate`, and
  `@LastModifiedDate`.

* **Impact:** Provides a standardized way to track who changed what and when
  across all entities.

---

## 2. Advanced Security Hardening

### A. High-Performance Token Strategy (Refresh Tokens)

* **Current Issue:** JWTs are simple strings with a fixed expiry. We lack a
  secure logout mechanism that doesn't compromise stateless performance.

* **Proposal:** Implement **Short-lived Access Tokens (15m)** and
  **Database-backed Refresh Tokens (7d)**.

  * Access tokens are validated statelessly (no DB call) for maximum speed.

  * Refresh tokens are checked against the DB only once every 15 minutes to
    rotate the access token.

* **Impact:** 99% of API requests remain perfectly stateless and
  high-performance, while the system gains a robust, revocable session
  management system.

### B. Method-Level Security

* **Current Issue:** Security is mostly path-based in `SecurityConfig`.

* **Proposal:** Enable `@EnableMethodSecurity`. Use `@PreAuthorize` on service
  methods to ensure users can only modify their own posts or profiles.

* **Impact:** Prevents "Insecure Direct Object Reference" (IDOR) vulnerabilities
  where one user could potentially delete another's post by spoofing the ID.

---

## 3. High-Performance Logic

### A. Mapper Modernization (MapStruct)

* **Current Issue:** Using `BeanUtils.copyProperties`. This uses reflection, is
  slow, and is "hidden" logic that fails silently if field names drift.

* **Proposal:** Migrate to **MapStruct**.

* **Impact:** Compile-time safe, high-performance mapping logic that is
  explicitly defined and easily debugged.

### B. Global Exception Handling Refinement

* **Current Issue:** `ControllerExceptionHandler` catches only `Exception.class`
  (Status 500).

* **Proposal:** Add specific handlers for `ResourceNotFoundException` (404),
  `BadRequestException` (400), and `MethodArgumentNotValidException` (400).

* **Impact:** Provides clear, semantic HTTP responses to clients, essential for
  high-quality API consumers.

---

## 4. Systematic Reflection Elimination

### A. Constructor Injection Transition

* **Current Issue:** Code uses Field Injection (`@Autowired` on private fields).
  This relies on reflection to bypass access modifiers and makes testing harder.

* **Proposal:** Transition all components to **Constructor Injection**.

* **Impact:** Zero-reflection bean wiring at runtime, faster application
  startup, and superior unit testability.

### B. Mapper Reflection Removal

* **Current Issue:** Utilities and Mappers currently rely on reflection-based
  property copying.

* **Proposal:** Enforce a strict policy of **Compile-Time Mapping** only (via
  MapStruct or manual builders).

* **Impact:** Eliminates expensive reflection cycles during request processing
  and prevents runtime errors due to field name changes.

---

## 5. Developer Experience (DX) & Observability

### A. Comprehensive Integration Testing

* **Current Issue:** Test coverage is minimal.

* **Proposal:** Implement **Testcontainers** for integration tests. Run tests
  against a real MySQL instance in Docker during CI.

* **Impact:** Eliminates "it works on H2 but fails on MySQL" bugs and ensures
  the persistence layer is truly robust.

### B. Annotation-Driven Observability

* **Current Issue:** Observability is passive. We lack deep insights into
  specific business logic latency and throughput.

* **Proposal:** Implement **Full-Spectrum Micrometer Annotations**.

  * Enable `@Timed` on all `@RestController` and `@Service` classes to capture
    automatic latency histograms (p95, p99).

  * Use `@Counted` for specific high-value business metrics (e.g.,
    `user.registered`, `post.created`).

  * Implement an **Observation Registry** aspect to capture success/failure tags
    automatically.

* **Impact:** Zero-boilerplate performance monitoring. Allows us to visualize
  the "Hot Paths" and bottlenecks of the application in real-time.

---

## 6. Performance Impact Analysis

Implementing these proposals will result in a more efficient use of system
resources and lower response latencies.

| Proposal | Latency Impact | Scalability Impact | Justification |
| :--- | :--- | :--- | :--- |
| **Graph Refactor** | -20% to -50% | 🚀 Massive | Replaces inefficient `@ElementCollection` Maps with indexed Join Entities. |
| **Reflection Removal**| -5% to -10% | 📈 High | Replaces Runtime Reflection (Mappers/Injection) with Compile-time code. |
| **MapStruct** | -5% to -10% | 📈 High | Replaces Runtime Reflection (BeanUtils) with Compile-time generated code. |
| **Refresh Tokens** | Neutral | ✅ Maintained | Preserves stateless JWT speed while adding revocable sessions. |
| **Auditing** | +1% (Negligible) | Neutral | Standardized metadata tracking with minimal overhead. |

---

## 7. Detailed Implementation Strategy

### Wave 1: Immediate Quality Wins

1. **Constructor Injection**: Mark all `@Autowired` fields as `final` and use
   `@RequiredArgsConstructor`.

2. **Semantic Exceptions**: Refactor `ControllerExceptionHandler` to return
   specific HTTP codes (400, 404, 403) based on exception type.

### Wave 2: Architectural Modernization

1. **MapStruct Migration**: Replace `BeanUtils` with compile-time mappers.

2. **JPA Auditing**: Implement `Auditable` base class and enable
   `@EnableJpaAuditing`.

### Wave 3: Scalability & Security

1. **Refresh Token Pattern**: Implement `/auth/refresh` and `/auth/logout` with
   DB-backed rotation.

2. **Social Graph Refactor**: Replace `@ElementCollection` with a dedicated
   `Follow` entity and indexed join table.

### Wave 4: Infrastructure & Full-Spectrum Observability

1. **Testcontainers**: Replace H2 with real containerized MySQL for integration
   tests.

2. **Micrometer Annotations**: Apply `@Timed` and `@Counted` globally to all
   Controllers and Services.

3. **OpenTelemetry Tracing**: Configure export to Jaeger/Zipkin for distributed
   request tracing.

---

## 8. Summary of Recommended Actions

| Category | Priority | Difficulty |
| :--- | :--- | :--- |
| **Specific Exception Handlers** | High | Low |
| **Reflection Removal** | High | Medium |
| **MapStruct Migration** | High | Medium |
| **JPA Auditing** | Medium | Low |
| **Follower Logic Refactor** | High | High |
| **Testcontainers Integration** | Medium | Medium |

This plan represents the "Better Way" to evolve Moo. By focusing on
architectural integrity and scalability now, we prevent massive technical debt
as the user base grows.
