package org.ciopecalina.invoicingapp.security;

import org.ciopecalina.invoicingapp.dtos.UserSecurityDto;
import org.ciopecalina.invoicingapp.repositories.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return email -> {
            System.out.println("Authenticating user: " + email);
            return userRepository.findByEmail(email)
                    .map(user -> new UserSecurityDto(
                            user.getId(),
                            user.getEmail(),
                            user.getName(),
                            user.getPassword(),
                            user.getIsApproved(),
                            user.getIsAdmin()
                    ))
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable()) // Deactivated to use Postman
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/register", "/login").permitAll()
                        .requestMatchers( "/admin/**").hasRole("ADMIN")
                        .requestMatchers( "/document/**").hasRole("USER")
                        .requestMatchers( "/invoices/**").hasRole("USER")
                        .requestMatchers( "/email/**").hasRole("USER")
                        .requestMatchers( "/users/**").hasRole("USER")
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public AuthenticationManager authManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(authProvider);
    }
}
