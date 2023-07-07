package com.webshop.demo.security.principal;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.List;

/* Zusammenfassend wandelt die Klasse JwtToPrincipalConverter ein DecodedJWT-Objekt in ein UserPrincipal-Objekt um, 
indem sie die Informationen aus dem JWT extrahiert und in das entsprechende Datenmodell konvertiert. */

@Component
public class JwtToPrincipalConverter {
    
    public UserPrincipal convert(DecodedJWT jwt) {
        return new UserPrincipal(
                Long.valueOf(jwt.getSubject()),
                jwt.getClaim("username").asString(),
                extractAuthoritiesFromClaim(jwt)
        );
    }

    private List<SimpleGrantedAuthority> extractAuthoritiesFromClaim(DecodedJWT jwt) {
        Claim claim = jwt.getClaim("roles");

        if (claim.isMissing() || claim.isNull()) {
            return List.of();
        }

        return claim.asList(SimpleGrantedAuthority.class);
    }

}
