package com.webshop.demo.security.filter;

import com.webshop.demo.security.jwt.JwtDecoder;
import com.webshop.demo.security.principal.JwtToPrincipalConverter;
import com.webshop.demo.security.principal.UserPrincipalAuthenticationToken;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

/* Der Filter wird verwendet, um eingehende HTTP-Anfragen zu überprüfen und sicherzustellen, dass sie einen gültigen JWT (JSON Web Token) enthalten.
 * 
 * Der Filter extrahiert den JWT aus dem "Authorization" Header der Anfrage mithilfe der Methode extractTokenFromRequest.
Der extrahierte JWT wird an den JwtDecoder übergeben, um ihn zu validieren und die darin enthaltenen Informationen zu entschlüsseln.
Der entschlüsselte JWT wird an den JwtToPrincipalConverter übergeben, um ihn in ein UserPrincipal-Objekt umzuwandeln, das die Details des authentifizierten Benutzers enthält.
Das UserPrincipal-Objekt wird verwendet, um ein UserPrincipalAuthenticationToken zu erstellen.
Das UserPrincipalAuthenticationToken wird dann dem SecurityContextHolder zugeordnet, um die Authentifizierungsinformationen für den aktuellen Benutzer zu setzen.
Der FilterChain wird fortgesetzt, indem die Anfrage an den nächsten Filter oder die entsprechende Handler-Methode weitergeleitet wird.
Der Zweck dieses Filters besteht darin, die Authentifizierung mit JWT in einer Webanwendung zu ermöglichen. Indem der Filter den JWT extrahiert,
validiert und in ein UserPrincipal-Objekt umwandelt, kann die Anwendung den Benutzer anhand des JWT authentifizieren und die entsprechenden Berechtigungen und Zugriffsrechte vergeben.


 */

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtDecoder jwtDecoder;
    private final JwtToPrincipalConverter jwtToPrincipalConverter;

    public JwtAuthenticationFilter(JwtDecoder jwtDecoder, JwtToPrincipalConverter jwtToPrincipalConverter) {
        this.jwtDecoder = jwtDecoder;
        this.jwtToPrincipalConverter = jwtToPrincipalConverter;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        extractTokenFromRequest(request)
                .map(jwtDecoder::decode)
                .map(jwtToPrincipalConverter::convert)
                .map(UserPrincipalAuthenticationToken::new)
                .ifPresent(auth -> SecurityContextHolder.getContext().setAuthentication(auth));

        filterChain.doFilter(request, response);
    }

    private Optional<String> extractTokenFromRequest(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        System.out.println("Received token: " + token); // Remove or replace with a logger in production code
        if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            return Optional.of(token.substring("Bearer ".length()));
        }
        return Optional.empty();
    }

}
