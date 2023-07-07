package com.webshop.demo.security.principal;

import org.springframework.security.authentication.AbstractAuthenticationToken;

/* Die UserPrincipalAuthenticationToken-Klasse wird verwendet, um den authentifizierten Benutzer zusammen mit 
seinen Berechtigungen (Rollen) im Sicherheitskontext zu speichern. Sie wird normalerweise nach erfolgreicher 
Authentifizierung erstellt und im SecurityContextHolder abgelegt. Dies ermöglicht den Zugriff auf den authentifizierten 
Benutzer und seine Berechtigungen während der Ausführung der Anwendung. */

public class UserPrincipalAuthenticationToken extends AbstractAuthenticationToken {

    private final UserPrincipal userPrincipal;

    public UserPrincipalAuthenticationToken(UserPrincipal userPrincipal) {
        super(userPrincipal.getAuthorities());
        this.userPrincipal = userPrincipal;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public UserPrincipal getPrincipal() {
        return userPrincipal;
    }
}
