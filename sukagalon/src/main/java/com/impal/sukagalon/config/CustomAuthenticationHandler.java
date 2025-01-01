package com.impal.sukagalon.config;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // Ambil peran dari pengguna
        String redirectUrl = "/Login"; // Default redirect URL
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            redirectUrl = "/Dashboard";
        } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_BUYER"))) {
            redirectUrl = "/Belanja";
        }

        // Redirect pengguna
        response.sendRedirect(redirectUrl);
    }
}

