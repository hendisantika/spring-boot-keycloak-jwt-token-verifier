package id.my.hendisantika.jwttokenverifier.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

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
@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authz -> authz.anyRequest().authenticated())
                .addFilterAfter(new CustomSecurityFilter(), BasicAuthenticationFilter.class)
                .build();
    }
}
