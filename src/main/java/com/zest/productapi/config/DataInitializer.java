package com.zest.productapi.config;

import com.zest.productapi.entity.*;
import com.zest.productapi.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {
    @Bean
    CommandLineRunner seed(UserRepository r, PasswordEncoder e) {
        return a -> {
            if (r.findByUsername("admin").isEmpty()) {
                AppUser u = new AppUser();
                u.setUsername("admin");
                u.setPassword(e.encode("Admin@123"));
                u.setRole("ADMIN");
                r.save(u);
            }
            if (r.findByUsername("user").isEmpty()) {
                AppUser u = new AppUser();
                u.setUsername("user");
                u.setPassword(e.encode("User@123"));
                u.setRole("USER");
                r.save(u);
            }
        };
    }
}
