package sysc4806.project.accounts;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(
                                "/public/**",
                                "/manifest.json",
                                "/static/**",
                                "/index.html",
                                "/title-image.png",
                                "/favicon.ico",
                                "/register",
                                "/login",
                                "/logout"
                        ).permitAll()
                        .requestMatchers(HttpMethod.POST, "/survey").authenticated()
                        .requestMatchers(HttpMethod.GET, "/survey/owned").authenticated()
                        .requestMatchers(HttpMethod.GET, "/survey/{id}/metrics").authenticated()
                        .anyRequest().permitAll() // Decided to make this permit all by default,
                        // This means we don't have to worry about resources that we haven't added here
                        // Only add here if there is a new authenticated endpoint/resource
                )
                .httpBasic(Customizer.withDefaults())
                .formLogin(form -> form
                        .loginPage("/")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/", true)
                        .failureUrl("/login?error=true")
                        .permitAll()
                ).logout(logout -> logout.permitAll());
        return http.build();
    }
}