package com.webshop.demo.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.webshop.demo.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

// Die Annotation @Component gibt an, dass diese Klasse ein Spring-Bean ist und von Spring zur Laufzeit instanziiert wird
@Component
public class JwtUtil {

    // @Value injiziert den Wert aus der Konfiguration (in der
    // application.properties Datei)
    // mit dem Schlüssel "jwt.secret" in die Variable secret
    @Value("${jwt.secret}")
    private String secret;

    // Konstante für die Ablaufzeit des JWT (15 Minuten), ausgedrückt in
    // Millisekunden
    private static final long EXPIRATION_TIME = 15 * 60 * 1000;

    // Methode zur Erstellung eines JWT für den gegebenen Benutzer
    public String generateToken(User user) {
        // Erstellen von Zeitstempeln für die Ausstellung des Tokens und dessen
        // Ablaufzeitpunkt
        Date issuedAt = new Date();
        Date expiresAt = new Date(issuedAt.getTime() + EXPIRATION_TIME);

        // Erstellen und Zurückgeben des JWT unter Verwendung der JWT-Bibliothek von
        // Auth0
        return JWT.create()
                .withSubject(user.getUsername()) // Setzen des Subjekts (i.d.R. der Benutzername) des Tokens
                .withIssuedAt(issuedAt) // Setzen des Ausstellungszeitpunkts des Tokens
                .withExpiresAt(expiresAt) // Setzen des Ablaufzeitpunkts des Tokens
                .withClaim("role", user.getRole().toString()) // Hinzufügen einer Rolle als benutzerdefinierten Anspruch
                // Signieren des Tokens mit dem HMAC512-Algorithmus unter Verwendung des in der
                // Konfiguration festgelegten "secret"
                .sign(Algorithm.HMAC512(secret));
    }
}
