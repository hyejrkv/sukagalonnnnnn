package com.impal.sukagalon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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

    @GetMapping("/buyer/sg-belanja")
    public String showBelanja(){
        return "SG_BELANJA";
    }
    
    @GetMapping("/admin/dashboard")
    public String showAdminDb(){
        return "ADMIN-DASHBOARD";
    }

    
}