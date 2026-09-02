package com.zest.productapi.security;

import com.zest.productapi.entity.AppUser;
import com.zest.productapi.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository repo;

    public CustomUserDetailsService(UserRepository r) {
        repo = r;
    }

    public UserDetails loadUserByUsername(String u) throws UsernameNotFoundException {
        AppUser x = repo.findByUsername(u).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return User.withUsername(x.getUsername()).password(x.getPassword()).authorities(new SimpleGrantedAuthority("ROLE_" + x.getRole())).build();
    }
}
