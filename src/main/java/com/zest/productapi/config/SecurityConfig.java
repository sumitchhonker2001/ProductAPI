package com.zest.productapi.config;

import com.zest.productapi.security.JwtAuthFilter;
import org.springframework.context.annotation.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.*;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.*;

import java.util.List;

@Configuration
@org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
public class SecurityConfig {
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



 @Bean
 SecurityFilterChain securityFilterChain(
         HttpSecurity http,
         JwtAuthFilter filter,
         CorsConfigurationSource corsConfigurationSource) throws Exception {

  return http
          .csrf(c -> c.disable())
          .cors(c -> c.configurationSource(corsConfigurationSource))
          .sessionManagement(s ->
                  s.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
          )
          .authorizeHttpRequests(a ->
                  a
                          .requestMatchers(
                                  "/api/v1/auth/**",
                                  "/swagger-ui/**",
                                  "/swagger-ui.html",
                                  "/v3/api-docs/**"
                          ).permitAll()

                          .requestMatchers(
                                  HttpMethod.GET,
                                  "/api/v1/products/**"
                          ).hasAnyRole("USER", "ADMIN")

                          .requestMatchers(
                                  "/api/v1/products/**"
                          ).hasRole("ADMIN")

                          .anyRequest().authenticated()
          )
          .addFilterBefore(
                  filter,
                  UsernamePasswordAuthenticationFilter.class
          )
          .build();
 }

//    @Bean
//    CorsConfigurationSource cors(@Value("${APP_CORS_ORIGIN:http://localhost:3000}") String origin) {
//        CorsConfiguration c = new CorsConfiguration();
//        c.setAllowedOrigins(List.of(origin));
//        c.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//        c.setAllowedHeaders(List.of("*"));
//        c.setAllowCredentials(true);
//        UrlBasedCorsConfigurationSource s = new UrlBasedCorsConfigurationSource();
//        s.registerCorsConfiguration("/**", c);
//        return s;
//    }
}
