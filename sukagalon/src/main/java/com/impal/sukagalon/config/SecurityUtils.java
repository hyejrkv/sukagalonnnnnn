package com.impal.sukagalon.config;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpSession;

@Component
public class SecurityUtils {
    
    public static boolean isUserInRole(HttpSession session, String role) {
        @SuppressWarnings("unchecked")
        Collection<? extends GrantedAuthority> authorities = 
            (Collection<? extends GrantedAuthority>) session.getAttribute("USER_ROLES");
        
        return authorities != null && 
               authorities.stream()
                   .anyMatch(a -> a.getAuthority().equals("ROLE_" + role));
    }
    
    public static String getCurrentUserEmail(HttpSession session) {
        return (String) session.getAttribute("USER_EMAIL");
    }
}