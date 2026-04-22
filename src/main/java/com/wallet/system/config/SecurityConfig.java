package com.wallet.system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth

                        // ✅ allow these without login
                        .requestMatchers("/api/users/register", "/api/users/login").permitAll()

                        // 🔒 everything else requires auth
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}