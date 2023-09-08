package com.webshop.demo.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.webshop.demo.model.User;
import java.util.Date;

public class JwtUtil {

    private static final String SECRET = "eyJhbGciOiJIUzM4NCJ9.eyJSb2xlIjoiQWRtaW4iLCJJc3N1ZXIiOiJJc3N1ZXIiLCJVc2VybmFtZSI6IkphdmFJblVzZSIsImV4cCI6MTY5NDE3NzQ2MiwiaWF0IjoxNjk0MTc3NDYyfQ.TYfPyDvxJOPIpNoZl4cWqkHwg0k6YFgG63vDREv-M6LFykGs7PITBCu-XidvVASh"; // Change this to a more secure key and possibly externalize it.
    private static final long EXPIRATION_TIME = 15 * 60 * 1000; // 15 minutes in milliseconds

    public static String generateToken(User user) {
        Date issuedAt = new Date();
        Date expiresAt = new Date(issuedAt.getTime() + EXPIRATION_TIME);

        return JWT.create()
            .withSubject(user.getUsername())
            .withIssuedAt(issuedAt)
            .withExpiresAt(expiresAt)
            .sign(Algorithm.HMAC512(SECRET));
    }
}
