package com.webshop.demo.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.webshop.demo.dto.LoginRequest;
import com.webshop.demo.dto.TokenResponse;
import com.webshop.demo.security.jwt.JwtIssuer;
import com.webshop.demo.security.principal.UserPrincipal;

import jakarta.validation.Valid;
import java.util.List;


@RestController
public class LoginController {
        private final JwtIssuer jwtIssuer;
    private final AuthenticationManager authenticationManager;

    public LoginController(
            JwtIssuer jwtIssuer,
            AuthenticationManager authenticationManager
    ) {
        this.jwtIssuer = jwtIssuer;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public TokenResponse login(@RequestBody @Valid LoginRequest loginRequest) {
        // pass the username and password to springs in-build security manager
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        // extract the user principle from the security context
        // the user is in the security context because the information was passed to the security manager
        SecurityContextHolder.getContext().setAuthentication(auth);
        UserPrincipal principal = (UserPrincipal) auth.getPrincipal();

        // issue the jwt with the user information
        List<String> roles = principal.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        String token = jwtIssuer.issue(
                principal.getId(),
                principal.getUsername(),
                roles
        );

        return new TokenResponse(token);
    }
}
