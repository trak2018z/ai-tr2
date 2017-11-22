package pl.tomaszpanek.aplikacjeinternetowe.domain.secuity.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import pl.tomaszpanek.aplikacjeinternetowe.domain.repository.AppUserRepository;
import pl.tomaszpanek.aplikacjeinternetowe.domain.secuity.JwtAuthenticationFilter;
import pl.tomaszpanek.aplikacjeinternetowe.domain.secuity.JwtAuthorizationFilter;
import pl.tomaszpanek.aplikacjeinternetowe.domain.secuity.SecurityConstants;

@Configuration
@EnableWebSecurity
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private AppUserRepository appUserRepository;
    private UserDetailsService userDetailsService;
    private SecurityConstants securityConstants;

    public WebSecurityConfig(UserDetailsService userDetailsService, AppUserRepository appUserRepository,
                             SecurityConstants securityConstants) {
        this.userDetailsService = userDetailsService;
        this.appUserRepository = appUserRepository;
        this.securityConstants = securityConstants;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/appuser/activate/**").permitAll() /* Account activation */
                .antMatchers(HttpMethod.POST, securityConstants.signUpUrl).permitAll() /* Register */
                .antMatchers("/api/**").authenticated() /* Api calls */
                .antMatchers(HttpMethod.GET, "/**").permitAll() /* Welcome page */
                .anyRequest().authenticated()
                .and()
                .addFilter(new JwtAuthenticationFilter(authenticationManager(), appUserRepository, securityConstants))
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), securityConstants));
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.applyPermitDefaultValues();
        corsConfiguration.addAllowedMethod(HttpMethod.PUT);
        corsConfiguration.addAllowedMethod(HttpMethod.PATCH);
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}