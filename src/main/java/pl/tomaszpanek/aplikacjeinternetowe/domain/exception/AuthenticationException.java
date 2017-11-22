package pl.tomaszpanek.aplikacjeinternetowe.domain.exception;

public class AuthenticationException extends RuntimeException {

    public AuthenticationException(Throwable cause) {
        super(cause);
    }
}
