package id.my.hendisantika.jwttokenverifier.config;

import id.my.hendisantika.jwttokenverifier.contoller.EmployeeController;
import id.my.hendisantika.jwttokenverifier.contoller.SecureController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({EmployeeController.class, SecureController.class})
@Import(TestSecurityConfig.class)
public class SecurityConfigurationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testUnauthenticatedAccess() throws Exception {
        mockMvc.perform(get("/secure-api"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    public void testAuthenticatedAccess() throws Exception {
        mockMvc.perform(get("/secure-api"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "backend-api.employee")
    public void testWithSpecificAuthority() throws Exception {
        mockMvc.perform(get("/employee/read"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "wrong-authority")
    public void testWithWrongAuthority() throws Exception {
        mockMvc.perform(get("/employee/read"))
                .andExpect(status().isForbidden());
    }
}
