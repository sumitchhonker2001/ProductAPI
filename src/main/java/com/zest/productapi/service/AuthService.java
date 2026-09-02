package com.zest.productapi.service;

import com.zest.productapi.dto.AuthDtos.*;
import com.zest.productapi.entity.RefreshToken;
import com.zest.productapi.exception.ApiException;
import com.zest.productapi.repository.RefreshTokenRepository;
import com.zest.productapi.security.JwtService;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
public class AuthService {
    private final AuthenticationManager auth;
    private final UserDetailsService users;
    private final JwtService jwt;
    private final RefreshTokenRepository repo;

    public AuthService(AuthenticationManager a, UserDetailsService u, JwtService j, RefreshTokenRepository r) {
        auth = a;
        users = u;
        jwt = j;
        repo = r;
    }

    @Transactional
    public TokenResponse login(LoginRequest req) {
        auth.authenticate(new UsernamePasswordAuthenticationToken(req.username(), req.password()));
        return issue(req.username());
    }

    @Transactional
    public TokenResponse refresh(RefreshRequest req) {
        RefreshToken old = repo.findByTokenValue(req.refreshToken()).orElseThrow(() -> new ApiException("Invalid refresh token"));
        if (old.isRevoked() || old.getExpiresAt().isBefore(Instant.now()))
            throw new ApiException("Refresh token expired or revoked");
        old.setRevoked(true);
        repo.save(old);
        return issue(old.getUsername());
    }

    private TokenResponse issue(String username) {
        UserDetails u = users.loadUserByUsername(username);
        String access = jwt.generateToken(u), refresh = UUID.randomUUID().toString();
        RefreshToken r = new RefreshToken();
        r.setTokenValue(refresh);
        r.setUsername(username);
        r.setExpiresAt(Instant.now().plusSeconds(7 * 24 * 3600));
        repo.save(r);
        return new TokenResponse(access, refresh, "Bearer", jwt.getExpiration());
    }
}
