package pl.tomaszpanek.aplikacjeinternetowe.domain.security;

import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.ArrayList;

public class Authentication {
    private final SecurityConstants securityConstants;

    public Authentication(SecurityConstants securityConstants) {
        this.securityConstants = securityConstants;
    }

    public UsernamePasswordAuthenticationToken getAuthentication(String token) {
        if (token != null) {
            String user = Jwts.parser()
                    .setSigningKey(securityConstants.secret)
                    .parseClaimsJws(token.replace(SecurityConstants.TOKEN_PREFIX, ""))
                    .getBody()
                    .getSubject();
            if (user != null) {
                return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
            }
        }
        return null;
    }
}
