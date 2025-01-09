package com.impal.sukagalon.controller;

//import java.util.Map;

//import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.impal.sukagalon.models.User;
import com.impal.sukagalon.services.UserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/Daftar")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "SG_DAFTAR";
    }

    @PostMapping("/Daftar")
    public String registerUser(@ModelAttribute User user, RedirectAttributes redirectAttributes,
                                @RequestParam String email, 
                                @RequestParam String password,
                                @RequestParam(required = false) String confirmPassword) {
        
        if (password.length()<6){
            redirectAttributes.addFlashAttribute("error", "Password harus minimal 6 karakter!");
            return ("redirect:/Daftar");
        } else if (confirmPassword != null && !password.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("error", "Password dan konfirmasi password tidak cocok!");
            return ("redirect:/Daftar");
        }
        try {
            user.setRole("BUYER");
            userService.registerUser(user);
            redirectAttributes.addFlashAttribute("message", "Registration successful!");
            return "redirect:/Login";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/Daftar";
        }

    }
    
    @GetMapping("/Login")
    public String showLoginPage() {
        return "SG_LOGIN";
    }

    @PostMapping("/Login")
    public String handleLogin(@RequestParam String email, @RequestParam String password, Model model,
            HttpSession session) {
        User user = userService.login(email, password);
        // System.out.println("USER:" + user);
        if (user != null) {
            session.setAttribute("user", user);
            model.addAttribute("user", user);
            if (user.getRole().equals("ADMIN")){
                return "redirect:/Dashboard";
            }else {
                return "redirect:/Belanja";
            }
        } else {
            model.addAttribute("error", "Invalid email or password");
            return "SG_LOGIN";
        }
    }

    @GetMapping("/Logout")
    public String logout(HttpSession session, HttpServletResponse response) {
        session.invalidate();
        SecurityContextHolder.clearContext();
        // Clear the "Remember Me" cookie
        Cookie cookie = new Cookie("rememberMe", null);
        cookie.setMaxAge(0); // Expire the cookie
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        return "redirect:/";
    }
}
