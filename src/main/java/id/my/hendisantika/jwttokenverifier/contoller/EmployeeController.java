package id.my.hendisantika.jwttokenverifier.contoller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * Project : jwt-token-verifier
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 18/04/25
 * Time: 09.42
 * To change this template use File | Settings | File Templates.
 */
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @PreAuthorize("hasAuthority('backend-api.employee') or hasAuthority('backend-api.supervisor')")
    @RequestMapping("/read")
    public Map<String, String> readEmployee() {
        return Map.of("name", "Yuji", "role", "Developer", "age", "25");
    }

    @PreAuthorize("hasAuthority('backend-api.manager')")
    @RequestMapping("/update")
    public Map<String, String> updateEmployee() {
        return Map.of("message", "Updated successfully");
    }
}
