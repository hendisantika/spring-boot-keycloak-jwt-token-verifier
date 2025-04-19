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

@WebMvcTest(EmployeeController.class)
@Import(TestSecurityConfig.class)
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(authorities = "backend-api.employee")
    public void testReadEmployeeWithEmployeeAuthority() throws Exception {
        mockMvc.perform(get("/employee/read"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Yuji"))
                .andExpect(jsonPath("$.role").value("Developer"))
                .andExpect(jsonPath("$.age").value("25"));
    }

    @Test
    @WithMockUser(authorities = "backend-api.supervisor")
    public void testReadEmployeeWithSupervisorAuthority() throws Exception {
        mockMvc.perform(get("/employee/read"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Yuji"))
                .andExpect(jsonPath("$.role").value("Developer"))
                .andExpect(jsonPath("$.age").value("25"));
    }

    @Test
    @WithMockUser(authorities = "backend-api.manager")
    public void testReadEmployeeWithManagerAuthority() throws Exception {
        mockMvc.perform(get("/employee/read"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "backend-api.manager")
    public void testUpdateEmployeeWithManagerAuthority() throws Exception {
        mockMvc.perform(get("/employee/update"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Updated successfully"));
    }

    @Test
    @WithMockUser(authorities = "backend-api.employee")
    public void testUpdateEmployeeWithEmployeeAuthority() throws Exception {
        mockMvc.perform(get("/employee/update"))
                .andExpect(status().isForbidden());
    }
}
