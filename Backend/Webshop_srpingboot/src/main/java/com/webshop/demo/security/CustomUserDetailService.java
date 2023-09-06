package com.webshop.demo.security;

import com.webshop.demo.model.User;
import com.webshop.demo.security.principal.UserPrincipal;
import com.webshop.demo.service.UserService;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

/* Insgesamt wird die CustomUserDetailService-Klasse verwendet, um die Benutzerdetails zu laden
und einen UserPrincipal-Objekt bereitzustellen, das die Informationen über den authentifizierten 
 Benutzer enthält. */

@Component
public class CustomUserDetailService implements UserDetailsService {
    
    private final UserService userService;

    public CustomUserDetailService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService
                .findByUsername(username)
                .orElseThrow();

        return new UserPrincipal(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority(user.getRole()))
        );
    }
}
