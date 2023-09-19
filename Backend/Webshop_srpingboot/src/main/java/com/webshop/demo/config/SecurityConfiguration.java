package com.webshop.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration {
    
    @Bean
    public SecurityFilterChain applicationSecurity (HttpSecurity http) throws Exception{
        http.cors().disable()
        .csrf().disable()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                    .formLogin().disable()
                    .securityMatcher("/**")
                    .authorizeHttpRequests(registry -> registry
                    .requestMatchers(HttpMethod.GET, "/products", "/products/*").permitAll());
                    return http.build();
    }
}
