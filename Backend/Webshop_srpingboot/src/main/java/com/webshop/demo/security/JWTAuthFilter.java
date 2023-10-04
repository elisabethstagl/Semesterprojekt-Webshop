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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
// Die Klasse JWTAuthFilter erweitert OncePerRequestFilter, um sicherzustellen,
// dass der Filter genau einmal pro Anfrage durchlaufen wird
public class JWTAuthFilter extends OncePerRequestFilter {

    // Autowire (Injizieren) des UserDetailsService.
    // Die Annotation @Lazy gibt an, dass die Dependency erst bei ihrer ersten
    // Verwendung erstellt und autowired werden soll
    @Autowired
    @Lazy
    private UserDetailsService userDetailsService;

    // Injizieren des jwt.secret-Werts aus den Konfigurationsdateien in die Variable
    // "secret"
    @Value("${jwt.secret}")
    private String secret;

    // Logger-Instanz zum Protokollieren von Informationen und Fehlern
    private static final Logger logger = LoggerFactory.getLogger(JWTAuthFilter.class);

    // Überschreiben der doFilterInternal-Methode von OncePerRequestFilter
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Extrahieren des Authorization-Headers aus der HTTP-Anfrage
        String header = request.getHeader("Authorization");

        // Überprüfen, ob der Header vorhanden ist und mit "Bearer " beginnt
        if (header == null || !header.startsWith("Bearer ")) {
            // Falls nicht, die Anfrage ohne Authentifizierung weiterleiten und die Methode
            // beenden
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // Entfernen des "Bearer "-Präfixes vom Token
            String token = header.replace("Bearer ", "");

            // Überprüfen des Tokens unter Verwendung des Secrets und Extrahieren des
            // Benutzernamens
            String username = JWT.require(Algorithm.HMAC512(secret))
                    .build()
                    .verify(token)
                    .getSubject();

            // Wenn ein Benutzername im Token gefunden wurde, fortfahren
            if (username != null) {
                // Laden der Benutzerdetails mit dem ermittelten Benutzernamen
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                // Abrufen der Rollen/Behörden aus den Benutzerdetails
                Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

                // Erstellen und Setzen des Authentifizierungstokens im SecurityContext
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, authorities);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);

                // Protokollieren der erfolgreichen Authentifizierung
                logger.info("Authenticated user: {}, with roles: {}", userDetails.getUsername(), authorities);
            }
        } catch (Exception e) {
            // Bei einer Ausnahme (z. B. einem ungültigen Token) die Fehlermeldung
            // protokollieren
            logger.error("Failed to authenticate user using JWT", e);
        }

        // Weiterleitung der Anfrage zur nächsten Filter in der Kette
        filterChain.doFilter(request, response);
    }
}