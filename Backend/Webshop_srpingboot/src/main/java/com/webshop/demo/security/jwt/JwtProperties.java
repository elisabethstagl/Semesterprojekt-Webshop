package com.webshop.demo.security.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/* 
Die Klasse JwtProperties in deinem Code ist eine Konfigurationsklasse für die JWT-Eigenschaften.

Die Klasse ist mit den Annotationen @Configuration und @ConfigurationProperties("security.jwt") markiert. Dadurch wird sie als eine Spring-Konfigurationsklasse definiert und die Eigenschaften werden aus der application.properties- oder application.yml-Datei gelesen. */

@Configuration
@ConfigurationProperties("security.jwt")
public class JwtProperties {
    
    private String secret;

    public String getSecret(){
        System.out.println("Getting JWT secret: " + secret); // Remove or replace with a logger in production code
        return secret;
    }


    public void setSecret(String secret){
        this.secret = secret;
    }
}
