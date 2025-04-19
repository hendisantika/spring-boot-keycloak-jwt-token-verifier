package id.my.hendisantika.jwttokenverifier.integration;

import id.my.hendisantika.jwttokenverifier.config.TestSecurityConfig;
import id.my.hendisantika.jwttokenverifier.contoller.EmployeeController;
import id.my.hendisantika.jwttokenverifier.contoller.SecureController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration test for JWT authentication.
 * <p>
 * Note: This test uses @WithMockUser to simulate authentication instead of real JWT tokens.
 * For a complete end-to-end test with real JWT tokens, you would need:
 * 1. A test Keycloak server
 * 2. A way to generate valid JWT tokens
 * 3. A custom test configuration that points to the test Keycloak server
 */
@WebMvcTest({EmployeeController.class, SecureController.class})
@Import(TestSecurityConfig.class)
public class JwtAuthenticationIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(authorities = {"backend-api.employee"})
    public void testEmployeeEndpointWithValidToken() throws Exception {
        mockMvc.perform(get("/employee/read"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Yuji"))
                .andExpect(jsonPath("$.role").value("Developer"))
                .andExpect(jsonPath("$.age").value("25"));
    }

    @Test
    @WithMockUser(authorities = {"backend-api.manager"})
    public void testManagerEndpointWithValidToken() throws Exception {
        mockMvc.perform(get("/employee/update"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Updated successfully"));
    }

    @Test
    @WithMockUser
    public void testSecureEndpointWithValidToken() throws Exception {
        mockMvc.perform(get("/secure-api"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Hello"));
    }

    @Test
    public void testEndpointWithoutToken() throws Exception {
        mockMvc.perform(get("/secure-api"))
                .andExpect(status().isUnauthorized());
    }
}
