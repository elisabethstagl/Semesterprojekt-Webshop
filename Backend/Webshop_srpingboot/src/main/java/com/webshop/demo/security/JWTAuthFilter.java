package com.webshop.demo.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class JWTAuthFilter extends OncePerRequestFilter {

    @Autowired
    @Lazy
    private UserDetailsService userDetailsService;

    @Value("${jwt.secret}") 
    private String secret;

    private static final Logger logger = LoggerFactory.getLogger(JWTAuthFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            // If the request does not contain a valid token, continue without authentication.
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String token = header.replace("Bearer ", "");

            // Verify the token with the correct secret.
            String username = JWT.require(Algorithm.HMAC512(secret))
                .build()
                .verify(token)
                .getSubject();

            if (username != null) {
                // Load user details from the userDetailsService.
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                // Make sure the user exists and then fetch roles/authorities from userDetails.
                Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

                // Create and set the authentication token.
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                
                // Log successful authentication.
                logger.info("Authenticated user: {}, with roles: {}", userDetails.getUsername(), authorities);
            }
        } catch (Exception e) {
            // Log authentication failure.
            logger.error("Failed to authenticate user using JWT", e);
        }

        // Continue with the filter chain.
        filterChain.doFilter(request, response);
    }
}
