package id.my.hendisantika.jwttokenverifier.contoller;

import id.my.hendisantika.jwttokenverifier.config.TestSecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SecureController.class)
@Import(TestSecurityConfig.class)
public class SecureControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    public void testSecureEndpointWithAuthenticatedUser() throws Exception {
        mockMvc.perform(get("/secure-api"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Hello"));
    }

    @Test
    public void testSecureEndpointWithoutAuthentication() throws Exception {
        mockMvc.perform(get("/secure-api"))
                .andExpect(status().isUnauthorized());
    }
}
