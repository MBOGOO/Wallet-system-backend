package com.wallet.system.security;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

public class JwtFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;

        // 🔑 Get Authorization header
        String header = req.getHeader("Authorization");

        // ✅ Check if token exists
        if (header != null && header.startsWith("Bearer ")) {

            String token = header.substring(7);

            try {
                // 🔐 Validate token
                String email = JwtUtil.extractEmail(token);

                // 📌 Attach user info to request
                req.setAttribute("userEmail", email);

            } catch (Exception e) {
                throw new RuntimeException("Invalid or expired token");
            }
        }

        // 🚀 Continue request
        chain.doFilter(request, response);
    }
}