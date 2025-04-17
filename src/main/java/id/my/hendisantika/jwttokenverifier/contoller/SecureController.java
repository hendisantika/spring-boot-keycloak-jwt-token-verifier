package id.my.hendisantika.jwttokenverifier.contoller;

import org.springframework.web.bind.annotation.GetMapping;
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
 * Date: 17/04/25
 * Time: 09.26
 * To change this template use File | Settings | File Templates.
 */
@RestController
@RequestMapping("/secure-api")
public class SecureController {
    @GetMapping
    public Map sayHello() {
        return Map.of("message", "Hello");
    }
}
