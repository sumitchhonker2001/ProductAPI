package com.zest.productapi.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.*;

@Service
public class JwtService {
    private final SecretKey key;
    private final long expiration;

    public JwtService(@Value("${app.jwt.secret}") String secret, @Value("${app.jwt.expiration-seconds:900}") long expiration) {
        key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expiration = expiration;
    }

    public String generateToken(UserDetails u) {
        Instant now = Instant.now();
        return Jwts.builder().subject(u.getUsername()).issuedAt(Date.from(now)).expiration(Date.from(now.plusSeconds(expiration))).claim("roles", u.getAuthorities().stream().map(a -> a.getAuthority()).toList()).signWith(key).compact();
    }

    public String username(String token) {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().getSubject();
    }

    public boolean valid(String token, UserDetails u) {
        try {
            return username(token).equals(u.getUsername()) && !expired(token);
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private boolean expired(String t) {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(t).getPayload().getExpiration().before(new Date());
    }

    public long getExpiration() {
        return expiration;
    }
}
