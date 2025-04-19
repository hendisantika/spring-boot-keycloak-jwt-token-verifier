# Testing the Spring Boot Keycloak JWT Token Verifier

This document provides information on how to test the Spring Boot Keycloak JWT Token Verifier application.

## Test Structure

The tests are organized into the following categories:

1. **Unit Tests for Controllers**
   - `EmployeeControllerTest`: Tests the EmployeeController with different authorities
   - `SecureControllerTest`: Tests the SecureController with and without authentication

2. **Unit Tests for Security Configuration**
   - `SecurityConfigurationTest`: Tests the security configuration at a higher level
   - `CustomSecurityFilterTest`: Tests the CustomSecurityFilter in isolation

3. **Integration Tests**
   - `JwtAuthenticationIntegrationTest`: Tests the complete authentication flow

## Running the Tests

You can run the tests using Gradle:

```bash
./gradlew test
```

To run a specific test class:

```bash
./gradlew test --tests "id.my.hendisantika.jwttokenverifier.contoller.EmployeeControllerTest"
```

## Test Approach

### Unit Tests

The unit tests use Spring's testing support to test components in isolation:

- `@WebMvcTest` for controller tests
- `@MockMvc` for simulating HTTP requests
- `@WithMockUser` for simulating authenticated users with specific authorities

### Integration Tests

The integration tests use `@SpringBootTest` to load the entire application context and test the components together.

### JWT Token Testing

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