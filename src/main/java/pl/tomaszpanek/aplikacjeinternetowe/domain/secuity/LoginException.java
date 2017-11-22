package pl.tomaszpanek.aplikacjeinternetowe.domain.secuity;

import org.springframework.security.core.AuthenticationException;

public class LoginException extends AuthenticationException {

    public LoginException(String message) {
        super(message);
    }
}
