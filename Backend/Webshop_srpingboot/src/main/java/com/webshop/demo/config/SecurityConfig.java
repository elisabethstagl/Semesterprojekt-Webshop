package com.webshop.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.webshop.demo.security.JWTAuthFilter;

import jakarta.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
public class SecurityConfig {

    @Autowired
    private JWTAuthFilter jwtAuthFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable() // Disable CSRF for simplicity. Reconsider for production!
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Set session management to stateless
            .and()
            .addFilterBefore((Filter) jwtAuthFilter, UsernamePasswordAuthenticationFilter.class) // Add JWT filter before UsernamePasswordAuthenticationFilter
            .authorizeHttpRequests()
                .requestMatchers(HttpMethod.GET, "/products", "/products/*").permitAll()
                .requestMatchers( "/users/register", "/users/login", "registrierung.html", "login.html", "index.html", "/users", "/users/current-user").permitAll()  // Allow access to registration and login endpoints
                .requestMatchers("/admin/**").hasRole("ADMIN") // Restrict access to admin endpoints
                .anyRequest().authenticated()         // All other requests need authentication
            .and()
            .logout()
                .permitAll();
        return http.build();
    }

}
