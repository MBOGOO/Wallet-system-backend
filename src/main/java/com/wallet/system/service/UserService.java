package com.wallet.system.service;

import com.wallet.system.entity.User;
import com.wallet.system.repository.UserRepository;
import com.wallet.system.security.JwtUtil;
//import com.wallet.system.dto.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // =========================
    // REGISTER USER
    // =========================
    public User registerUser(User user) {

        // encrypt password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    // =========================
    // LOGIN USER (JWT GENERATION)
    // =========================
    public String login(String email, String password) {

        // 1. Find user in DB
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2. Check password
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        // 3. Generate JWT token
        String token = JwtUtil.generateToken(user.getEmail());

        // 4. Return response (token + email)
        return token;
    }
}