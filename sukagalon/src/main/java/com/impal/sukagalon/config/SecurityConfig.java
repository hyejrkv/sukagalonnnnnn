package com.impal.sukagalon.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

//import com.impal.sukagalon.services.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests((auth) -> auth.anyRequest().permitAll()).build();
    }
    /*  private final CustomUserDetailsService customUserDetailsService;

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
                                 "/SG_RESETPASS.css", "/SG_RESETPASS.html", "/SG_RESETPASS.js", "/Reset", "/Belanja", 
                                 "/SG_BELANJA.html", "/SG_BELANJA.css", "/SG_BELANJA.js", "/static/**","/*.png", "/*.jpg", 
                                 "/*.jpeg", "/*.css", "/*.js", "/Dashboard","/ADMIN-DASHBOARD.html", "/ADMIN-DASHBOARD.css",
                                 "/ADMIN-DASHBOARD.js", "/ADMIN-KELOLA-PRODUK.html","/ADMIN-KELOLA-PRODUK.css",
                                 "/ADMIN-KELOLA-PRODUK.js", "/ADMIN-HISTORY.html", "/ADMIN-HISTORY.css", "/ADMIN-HISTORY.js",
                                 "/AdminProduk", "/AdminHistory", "/ADMIN-KELOLA-PRODUK.html", "/ADMIN-HISTORY.html").permitAll() // Allow public access to landing page and static resources
                .requestMatchers("/admin/**","/KelolaProduk", "AdminHistory", "/Dashboard","/ADMIN-DASHBOARD.html", "/ADMIN-DASHBOARD.css","/ADMIN-DASHBOARD.js", "/ADMIN-KELOLA-PRODUK.html","/ADMIN-KELOLA-PRODUK.css","/ADMIN-KELOLA-PRODUK.js", "/ADMIN-HISTORY.html", "/ADMIN-HISTORY.css", "/ADMIN-HISTORY.js").hasRole("ADMIN")
                .requestMatchers("/buyer/**").hasRole("BUYER")
                .anyRequest().authenticated() // Secure all other endpoints
            )
            .formLogin(login -> login
                .loginPage("/Login")
                .successHandler(new CustomAuthenticationHandler())
                .failureUrl("/Login?error=true")
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
    */
}
