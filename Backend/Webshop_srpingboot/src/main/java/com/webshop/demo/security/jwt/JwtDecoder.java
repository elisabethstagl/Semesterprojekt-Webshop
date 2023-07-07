package com.webshop.demo.security.jwt;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

/*Die Klasse JwtDecoder ist dafür verantwortlich, einen JWT (JSON Web Token) zu entschlüsseln und zu 
überprüfen. Zusammenfassend wird die Klasse JwtDecoder verwendet, um den JWT zu entschlüsseln und 
sicherzustellen, dass er mit dem richtigen Geheimnis signiert wurde. Dies ist ein wichtiger Schritt in
 der Verarbeitung des JWTs und dient der Sicherstellung der Authentizität und Integrität des Tokens. */

@Component
public class JwtDecoder {

    private final JwtProperties jwtProperties;

    public JwtDecoder(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    public DecodedJWT decode(String token) {
        return JWT
                .require(Algorithm.HMAC256(jwtProperties.getSecret()))
                .build()
                .verify(token);
    }
    
}
