package com.webshop.demo.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.webshop.demo.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}") // Read secret from application.properties or application.yml
    private String secret; // Remove the "static" keyword

    private static final long EXPIRATION_TIME = 15 * 60 * 1000; // 15 minutes in milliseconds

    public String generateToken(User user) {
        Date issuedAt = new Date();
        Date expiresAt = new Date(issuedAt.getTime() + EXPIRATION_TIME);

        return JWT.create()
            .withSubject(user.getUsername())
            .withIssuedAt(issuedAt)
            .withExpiresAt(expiresAt)
            .sign(Algorithm.HMAC512(secret)); // Use the secret key read from configuration
    }
}
