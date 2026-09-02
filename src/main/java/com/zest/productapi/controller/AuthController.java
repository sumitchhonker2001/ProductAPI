package com.zest.productapi.controller;
import com.zest.productapi.dto.AuthDtos.*; import com.zest.productapi.service.AuthService; import jakarta.validation.Valid; import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/v1/auth") public class AuthController {private final AuthService s;public AuthController(AuthService s){this.s=s;} @PostMapping("/login") public TokenResponse login(@Valid @RequestBody LoginRequest r){return s.login(r);} @PostMapping("/refresh") public TokenResponse refresh(@Valid @RequestBody RefreshRequest r){return s.refresh(r);}}
