# Spring Boot Keycloak JWT Token Verifier

[![Java CI with Gradle](https://github.com/hendisantika/spring-boot-keycloak-jwt-token-verifier/actions/workflows/gradle.yml/badge.svg)](https://github.com/hendisantika/spring-boot-keycloak-jwt-token-verifier/actions/workflows/gradle.yml)
![Java](https://img.shields.io/badge/Java-25-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1.1-brightgreen)
![Gradle](https://img.shields.io/badge/Gradle-9.8.2-blue)

This project demonstrates how to implement JWT token verification using Spring Boot and Keycloak. It includes a set of
REST endpoints protected by different authorization levels.

## Tech Stack

| Component        | Version |
|------------------|---------|
| Java (toolchain) | 25      |
| Spring Boot      | 4.1.1   |
| Spring Security  | 7.x     |
| Gradle (wrapper) | 9.8.2   |
| Auth0 java-jwt   | 4.6.2   |
| Auth0 jwks-rsa   | 0.24.1  |
| Keycloak         | 26.2.0  |

## Prerequisites

- JDK 25 (the Gradle toolchain is set to Java 25; e.g. [Eclipse Temurin 25](https://adoptium.net/))
- Docker and Docker Compose (for Keycloak and PostgreSQL)

## Project Structure

The project consists of the following main components:

1. **Controllers**
   - `EmployeeController`: Provides endpoints for employee operations with different authorization levels
   - `SecureController`: A simple secured endpoint that requires authentication

2. **Security Configuration**
   - `SecurityConfiguration`: Configures Spring Security to require authentication for all requests
   - `CustomSecurityFilter`: Verifies JWT tokens issued by Keycloak

## Running the Application

To run the application, you need a Keycloak server running. The project includes Docker Compose files to set up the
required infrastructure.

1. Start the Keycloak server:

```bash
docker-compose -f compose.yml up -d
```

2. Run the application:

```bash
./gradlew bootRun
```

3. Access the endpoints:

- `http://localhost:8080/secure-api`: Requires authentication
- `http://localhost:8080/employee/read`: Requires the `backend-api.employee` or `backend-api.supervisor` authority
- `http://localhost:8080/employee/update`: Requires the `backend-api.manager` authority

## Authentication

To access the protected endpoints, you need to obtain a JWT token from Keycloak and include it in the Authorization
header of your requests:

```
Authorization: Bearer <your-jwt-token>
```

The token must be issued by the Keycloak server running at `http://localhost:7080/realms/Hanzou` and must include the
appropriate authorities.

## Testing the Application

### Test Structure

The tests are organized into the following categories:

1. **Unit Tests for Controllers**
   - `EmployeeControllerTest`: Tests the EmployeeController with different authorities
   - `SecureControllerTest`: Tests the SecureController with and without authentication

2. **Unit Tests for Security Configuration**
   - `SecurityConfigurationTest`: Tests the security configuration at a higher level
   - `CustomSecurityFilterTest`: Tests the CustomSecurityFilter in isolation

3. **Integration Tests**
   - `JwtAuthenticationIntegrationTest`: Tests the complete authentication flow

### Running the Tests

You can run the tests using Gradle:

```bash
./gradlew test
```

To run a specific test class:

```bash
./gradlew test --tests "id.my.hendisantika.jwttokenverifier.contoller.EmployeeControllerTest"
```

### Test Approach

#### Unit Tests

The unit tests use Spring's testing support to test components in isolation:

- `@WebMvcTest` (`org.springframework.boot.webmvc.test.autoconfigure`, from `spring-boot-starter-webmvc-test`) for
  controller tests
- `MockMvc` for simulating HTTP requests
- `@WithMockUser` for simulating authenticated users with specific authorities

#### Integration Tests

The integration tests use `@WebMvcTest` to test the controllers with a mock security configuration.

#### JWT Token Testing

The tests use `@WithMockUser` to simulate authentication instead of real JWT tokens. For a complete end-to-end test with
real JWT tokens, you would need:

1. A test Keycloak server
2. A way to generate valid JWT tokens
3. A custom test configuration that points to the test Keycloak server

## Setting Up a Test Keycloak Server

For more comprehensive testing, you can set up a test Keycloak server using Docker:

```bash
docker run -p 7080:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:latest start-dev
```

Then, you would need to:

1. Create a realm called "Hanzou"
2. Create a client called "backend-api"
3. Create roles and users with appropriate permissions
4. Generate tokens for testing

## Mocking JWT Tokens

For advanced testing, you can create a mock JWT provider that returns predefined tokens. This approach allows you to
test the JWT verification logic without a real Keycloak server.

Example of a mock JWT provider:

```java

@TestConfiguration
public class TestJwtConfiguration {
   @Bean
   @Primary
   public JwkProvider jwkProvider() {
      // Return a mock JwkProvider that provides predefined keys
   }
}
```

## Continuous Integration

The [Java CI with Gradle](.github/workflows/gradle.yml) workflow runs on every push and pull request to `main`:

- **build**: sets up Temurin JDK 25 and runs `./gradlew build` (compile + all tests)
- **dependency-submission**: submits the Gradle dependency graph so GitHub can raise Dependabot security alerts

## Dependency Updates

[Dependabot](.github/dependabot.yml) keeps dependencies current, using conventional commit prefixes:

| Ecosystem      | Schedule                 | Grouping                                                            | Commit prefix   |
|----------------|--------------------------|---------------------------------------------------------------------|-----------------|
| Gradle         | Daily, 05:00 WIB         | `spring-boot`, `auth0`, and other `minor-and-patch` updates         | `build(deps)`   |
| GitHub Actions | Daily, 05:00 WIB         | All actions in one PR                                               | `ci(deps)`      |
| Docker Compose | Weekly (Monday) 05:00 WIB | PostgreSQL and Keycloak images in `compose.yml` / `compose3.yaml` | `chore(deps)`   |
