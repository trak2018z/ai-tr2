package pl.tomaszpanek.aplikacjeinternetowe.domain.secuity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SecurityConstants {

    @Value("${forum.security-secret}")
    public String secret;

    @Value("${forum.expiration-time}")
    public long expirationTime;

    @Value("${forum.sign-up-url}")
    public String signUpUrl;

    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
}
