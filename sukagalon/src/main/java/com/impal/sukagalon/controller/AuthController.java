package com.impal.sukagalon.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.impal.sukagalon.models.User;
import com.impal.sukagalon.services.UserService;


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
    public String registerUser(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        try {
            user.setRole("BUYER");
            userService.registerUser(user);
            redirectAttributes.addFlashAttribute("message", "Registration successful!");
            return "redirect:/Login?registered=true";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/Daftar";
        }

    }

    @GetMapping("/Login")
    public String loginForm(Principal principal, User user){
        String role = user.getRole();
        if (principal!=null){
            if ("ADMIN".equalsIgnoreCase(role)){
                return ("ADMIN-DASHBOARD");
            } else if ("BUYER".equalsIgnoreCase(role)){
                return ("SG_BELANJA");
            }
        }
        return "SG_LOGIN";
    }

    /*@PostMapping("/Login")
    public String login(@RequestParam String email, @RequestParam String password, 
                       HttpSession session, RedirectAttributes redirectAttributes) {
        try {
            User user = userService.login(email, password);
            session.setAttribute("user", user);
            session.setMaxInactiveInterval(3600);
            if (user.getRole()==Role.ADMIN){
                return "redirect:/AdminDashboard";
            } else {
                return "redirect:/Belanja";
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/Login";
        }
    }*/
}
