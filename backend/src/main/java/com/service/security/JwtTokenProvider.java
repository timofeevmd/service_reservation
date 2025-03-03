package com.service.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtTokenProvider {

    private final SecretKey jwtSecretKey;

    @Value("${jwt.expiration}")
    private long jwtExpirationMs = 1800000 ;

    /**
     * Constructor initializes the secret key based on the provided value.
     *
     * @param jwtSecret secret key from configuration
     */
    public JwtTokenProvider(@Value("${jwt.secret}") String jwtSecret) {
        this.jwtSecretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    /**
     * Generates a JWT token based on authentication.
     *
     * @param authentication authentication object
     * @return JWT token as a string
     */
    public String generateToken(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Date now = new Date(System.currentTimeMillis());
        Date expiryDate = new Date(System.currentTimeMillis() + jwtExpirationMs);

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .claim("sessionId", UUID.randomUUID().toString())
                .signWith(jwtSecretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    /**
     * Extracts the username (subject) from the JWT token.
     *
     * @param token JWT token as a string
     * @return username
     */
    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    /**
     * Extracts the session ID from the JWT token.
     *
     * @param token JWT token as a string
     * @return session ID
     */
    public String getSessionIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.get("sessionId", String.class);
    }

    /**
     * Validates the JWT token.
     *
     * @param token JWT token as a string
     * @return true if the token is valid
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(Keys.hmacShaKeyFor(jwtSecretKey.getEncoded()))
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
