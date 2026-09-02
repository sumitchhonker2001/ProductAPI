package com.zest.productapi.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity @Table(name="refresh_token", indexes={@Index(name="idx_refresh_token_value", columnList="token_value", unique=true),@Index(name="idx_refresh_token_username", columnList="username")})
public class RefreshToken {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(name="token_value",nullable=false,unique=true,length=100) private String tokenValue;
 @Column(nullable=false,length=100) private String username;
 @Column(name="expires_at",nullable=false) private Instant expiresAt;
 @Column(nullable=false) private boolean revoked;
 public Long getId(){return id;} public String getTokenValue(){return tokenValue;} public void setTokenValue(String v){tokenValue=v;} public String getUsername(){return username;} public void setUsername(String v){username=v;} public Instant getExpiresAt(){return expiresAt;} public void setExpiresAt(Instant v){expiresAt=v;} public boolean isRevoked(){return revoked;} public void setRevoked(boolean v){revoked=v;}
}
