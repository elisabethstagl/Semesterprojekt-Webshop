package com.webshop.demo;

import java.security.SecureRandom;
import java.util.Base64;

public class KeyGenerator {
    public static void main(String[] args) {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[32]; // 32 bytes = 256 bits
        random.nextBytes(bytes);

        String secretKey = Base64.getEncoder().encodeToString(bytes);
        System.out.println(secretKey);
    }
}
