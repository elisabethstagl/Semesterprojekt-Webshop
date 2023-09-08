package com.webshop.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable() // Disable CSRF for simplicity. Reconsider for production!
            .authorizeHttpRequests()
                .requestMatchers("/users/register", "/users/login").permitAll()  // Allow access to registration and login endpoints
                .anyRequest().authenticated()         // All other requests need authentication
            .and()
            .logout().permitAll();                       // Allow logout for everyone

        return http.build();
    }
}
