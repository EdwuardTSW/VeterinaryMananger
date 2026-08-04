package com.mananger_veterinary.vetman.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    private static final long EXPIRATION_TIME = 3_600_000;

    private final String secret;

    public JwtUtil(@Value("${jwt.secret}") String secret) {
        this.secret = secret;
    }

    public String generateToken(String email) {
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey())
                .compact();
    }

    public String extractEmail(String token) {
        return extractClaims(token).getSubject();
    }

    public boolean isTokenValid(String token) {
        try {
            extractClaims(token);
            return true;
        } catch (Exception exception) {
            System.out.println(
                    "JWT validation failed: "
                            + exception.getClass().getSimpleName()
                            + " - "
                            + exception.getMessage()
            );

            return false;
        }
    }

    private Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSigningKey() {
        if (secret.length() < 32) {
            throw new IllegalArgumentException(
                    "The JWT secret must contain at least 32 characters"
            );
        }

        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
}