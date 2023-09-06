package com.webshop.demo.security.jwt;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.Duration;
import java.util.List;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

/* Zusammenfassend stellt die Klasse JwtIssuer ein JWT aus, das Informationen über den Benutzer und seine Rollen enthält. 
Das ausgestellte JWT kann dann verwendet werden, um die Authentifizierung und Autorisierung des Benutzers in der 
Webanwendung zu ermöglichen.*/

@Service
public class JwtIssuer {

    private final JwtProperties jwtProperties;

    public JwtIssuer(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    public String issue(Long userId, String username, List<String> roles){
        System.out.println("JWT secret in issuer: " + jwtProperties.getSecret()); // Remove or replace with a logger in production code
        return JWT.create()
                .withSubject(String.valueOf(userId))
                .withExpiresAt(Instant.now().plus(Duration.of(1, ChronoUnit.DAYS)))
                .withClaim("username", username)
                .withClaim("roles", roles)
                .sign(Algorithm.HMAC256(jwtProperties.getSecret()));
    }


}
