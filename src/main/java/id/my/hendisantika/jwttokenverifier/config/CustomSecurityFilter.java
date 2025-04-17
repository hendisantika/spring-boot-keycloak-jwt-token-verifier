package id.my.hendisantika.jwttokenverifier.config;

import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.JwkProviderBuilder;
import lombok.extern.slf4j.Slf4j;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Filter;

/**
 * Created by IntelliJ IDEA.
 * Project : jwt-token-verifier
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 17/04/25
 * Time: 09.18
 * To change this template use File | Settings | File Templates.
 */
@Slf4j
public class CustomSecurityFilter implements Filter {


    private final JwkProvider jwkProvider;

    public CustomSecurityFilter() throws MalformedURLException {
        jwkProvider = new JwkProviderBuilder(new URL("http://localhost:8080/realms/master/protocol/openid-connect/certs")).build();
    }
}
