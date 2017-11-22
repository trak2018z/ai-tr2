package pl.tomaszpanek.aplikacjeinternetowe.domain.secuity;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pl.tomaszpanek.aplikacjeinternetowe.domain.exception.AuthenticationException;
import pl.tomaszpanek.aplikacjeinternetowe.domain.model.AppUser;
import pl.tomaszpanek.aplikacjeinternetowe.domain.repository.AppUserRepository;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;
    private final AppUserRepository appUserRepository;
    private final SecurityConstants securityConstants;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, AppUserRepository appUserRepository,
                                   SecurityConstants securityConstants) {
        this.authenticationManager = authenticationManager;
        this.appUserRepository = appUserRepository;
        this.securityConstants = securityConstants;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) {
        try {
            AppUser credentials = new ObjectMapper().readValue(req.getInputStream(), AppUser.class);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = getAuthentication(credentials);
            Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            if (authentication.isAuthenticated()) {
                AppUser appUser = appUserRepository.findByUsername(credentials.getUsername());
                if (appUser.isActivated()) {
                    return authentication;
                }
                throw new LoginException("Please activate your account!");
            }
            throw new LoginException("Bad credentials");
        } catch (IOException e) {
            throw new AuthenticationException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
        String token = Jwts.builder()
                .setSubject(((User) auth.getPrincipal()).getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + securityConstants.expirationTime))
                .signWith(SignatureAlgorithm.HS512, securityConstants.secret)
                .compact();
        res.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(AppUser credentials) {
        return new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword(), new ArrayList<>());
    }
}