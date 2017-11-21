package pl.tomaszpanek.aplikacjeinternetowe.domain.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordFactory {

    private PasswordFactory() {

    }

    public static String bCryptEncode(String password) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.encode(password);
    }
}
