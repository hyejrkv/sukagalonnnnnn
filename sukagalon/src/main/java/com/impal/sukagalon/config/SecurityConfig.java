package com.impal.sukagalon.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.impal.sukagalon.services.CustomUserDetailsService;

@Configuration
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/index.css", "/index.js", "/koko.png", "/AboutUs.css", "/AboutUs.js", 
                                 "/Kontak.css", "/AboutUs.html", "/Kontak.html", "/Kontak.css", "/AboutUs", 
                                 "/powerenjer.jpg", "/Kontak", "/Login", "/SG_LOGIN.html", "/SG_LOGIN.css", 
                                 "/SG_LOGIN.js", "/Daftar", "/SG_DAFTAR.html", "/SG_DAFTAR.css", "/SG_DAFTAR.js",
                                 "/SG_RESETPASS.css", "/SG_RESETPASS.html", "/SG_RESETPASS.js", "/Reset").permitAll() // Allow public access to landing page and static resources
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/buyer/**").hasRole("BUYER")
                .anyRequest().authenticated() // Secure all other endpoints
            )
            .formLogin(login -> login
                .loginPage("/Login")
                .loginProcessingUrl("/perform_login")
                .successHandler((request, response, authentication) -> {
                    String role = authentication.getAuthorities().iterator().next().getAuthority();
                    if (role.equals("ROLE_ADMIN")) {
                        response.sendRedirect("/admin/dashboard");
                    } else {
                        response.sendRedirect("/buyer/sg-belanja");
                    }
                })
                
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/Logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/Login")
                .permitAll()
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .maximumSessions(1)
                .maxSessionsPreventsLogin(false)
            );

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

}
