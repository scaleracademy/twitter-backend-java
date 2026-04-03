First off, thank you for considering contributing to Moo! It's people like you
that make Moo such a great tool for the community.

This document provides guidelines and standards for contributing to this
project. Following these helps us maintain a high-quality, high-performance
codebase.

## Code of Conduct

By participating in this project, you are expected to uphold our
[Code of Conduct](CODE_OF_CONDUCT.md). Please report unacceptable behavior to
the project maintainers.

## Getting Started

### 1. Fork and Clone

- Fork the repository on GitHub.
- Clone your fork locally:

    ```bash
    git clone https://github.com/YOUR_USERNAME/twitter-backend-java.git
    cd twitter-backend-java
    ```

### 2. Environment Setup

- **JDK:** Ensure you have **Microsoft Build of OpenJDK 21** installed.
- **Maven:** Use the provided wrapper `./mvnw`.
- **Database:** Local MySQL or H2 (configured via profiles).
- **IDE:** IntelliJ IDEA is recommended (Google Java Format plugin encouraged).

## Development Standards

To keep the codebase clean and stable, we enforce the following rules:

### 1. Code Style and Formatting

- We use **Spotless** with **Google Java Format**.
- **Always** format your code before committing:

    ```bash
    ./mvnw spotless:apply
    ```

- The CI will fail if the code is not perfectly formatted.

### 2. Imports vs. Qualified Names

- **Rule:** Never use fully qualified package names inline (e.g. `java.util.List`).
- **Action:** Always use `import` statements at the top of the file.

### 3. Null Safety (JSpecify)

- Every Service and Repository method must be annotated with **JSpecify**.
- Use `@Nullable` for optional return types or parameters.
- Use `@NonNull` for mandatory values.
- Default to `@NonNull` unless there is a valid reason for nullability.

### 4. Robust Architecture

- **Layering:** strictly follow `Controller` -> `Service` -> `Repository`.
- **Models (DTOs):** Never expose JPA Entities directly in Controllers. Use
  `Model` classes and `Mapper` utilities.
- **Validation:** Use `jakarta.validation` constraints (`@NotBlank`, `@Size`,
  etc.) on all Models.
- **Scalability:** Always use `Pageable` for list endpoints. Returning `List<T>`
  is strictly prohibited for feed-based APIs.

### 5. Security

- Use the `Principal` object in Controllers to identify the authenticated user.
- Never use `UUID.randomUUID()` or hardcoded IDs for "current user" logic.

## Git Flow and Branching

1. **Branch Naming:**

    - Features: `feature/short-description`
    - Bug Fixes: `fix/issue-id-description`
    - Chores/Docs: `chore/description`

2. **Commit Messages:**

    - Use conventional commits (e.g., `feat: ...`, `fix: ...`, `docs: ...`).
    - Keep messages concise but descriptive.

3. **Rebasing:**

    - Always rebase your feature branch against the latest `master` before
      submitting a PR.

## Testing Requirements

- **Unit Tests:** Mandatory for all new logic in the `service` layer.
- **Integration Tests:** Required for new API endpoints.
- **Coverage:** Aim for high coverage. Ensure `mvn jacoco:report` shows no
  regressions.
- Run all tests locally before pushing:

    ```bash
    ./mvnw test
    ```

## Pull Request Process

1. **Update Documentation:** If you add or change an API, ensure the
   Swagger/OpenAPI metadata is updated.

2. **Self-Review:** Go through your changes and ensure no "TODOs" or debug logs
   are left behind.

3. **CI Check:** Ensure the `PR Checker` workflow passes on GitHub.

4. **Reviewers:** Assign at least one maintainer for review. Be responsive to
   feedback!

## License

By contributing, you agree that your contributions will be licensed under the
**GNU Affero General Public License v3.0**.
