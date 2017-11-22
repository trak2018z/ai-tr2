package pl.tomaszpanek.aplikacjeinternetowe.domain.secuity;

import io.jsonwebtoken.Jwts;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import pl.tomaszpanek.aplikacjeinternetowe.domain.model.AppUser;
import pl.tomaszpanek.aplikacjeinternetowe.domain.repository.AppUserRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static java.util.Collections.emptyList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private AppUserRepository appUserRepository;
    private SecurityConstants securityConstants;

    public UserDetailsServiceImpl(AppUserRepository appUserRepository, SecurityConstants securityConstants) {
        this.appUserRepository = appUserRepository;
        this.securityConstants = securityConstants;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        AppUser appUser = appUserRepository.findByUsername(username);
        if (appUser == null) {
            throw new UsernameNotFoundException(username);
        }
        return new User(appUser.getUsername(), appUser.getPassword(), emptyList());
    }

    public Optional<AppUser> getUserFromToken() {
        HttpServletRequest httpServletRequest = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = httpServletRequest.getHeader(SecurityConstants.HEADER_STRING);
        if (token != null) {
            String user = Jwts.parser()
                    .setSigningKey(securityConstants.secret)
                    .parseClaimsJws(token.replace(SecurityConstants.TOKEN_PREFIX, ""))
                    .getBody()
                    .getSubject();
            if (user != null) {
                return Optional.ofNullable(appUserRepository.findByUsername(user));
            }
        }
        return Optional.empty();
    }
}