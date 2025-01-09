package com.impal.sukagalon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.impal.sukagalon.models.User;

import jakarta.servlet.http.HttpSession;

@Controller
public class LandingPageController {

    @GetMapping("/")
    public String landingPage() {
        return "index"; // Return the name of the HTML template for the landing page
    }

    @GetMapping("/AboutUs")
    public String aboutUs(){
        return "AboutUs";
    }

    @GetMapping("/Kontak")
    public String kontak(){
        return "Kontak";
    }

    @GetMapping("/Dashboard")
    public String dashboardadmin(HttpSession session){
        User currentUser = (User) session.getAttribute("user");
        if (currentUser==null || !"ADMIN".equals(currentUser.getRole())){
            return "redirect:/accessDenied";
        }
        return "ADMIN-DASHBOARD";
    }
}