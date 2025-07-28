package ictech.u2_w3_d1_spring_security_jwt.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // to disable default form login (we'll use React)
        http.formLogin(formLogin -> formLogin.disable());
        // to disable CSRF (we'll not use it)
        http.csrf(csrf -> csrf.disable());
        // JWT doesn't use sessions (STATELESS)
        http.sessionManagement(sessions -> sessions.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        // to disable 401/403 default errors, custom auth, we'll choose on which endpoints
        http.authorizeHttpRequests(h -> h.requestMatchers("/**").permitAll());

        return http.build();
    }
}
