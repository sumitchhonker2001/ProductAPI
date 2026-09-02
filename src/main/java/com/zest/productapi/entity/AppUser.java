package com.zest.productapi.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "app_user", indexes = {@Index(name = "idx_user_username", columnList = "username", unique = true)})
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true, length = 100)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false, length = 30)
    private String role;

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String v) {
        username = v;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String v) {
        password = v;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String v) {
        role = v;
    }
}
