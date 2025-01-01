package com.impal.sukagalon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.impal.sukagalon.services.UserService;

@Controller
@RequestMapping("/AdminHistory")
public class AdmHistoryController {
    
    @Autowired
    private UserService userService;

    @GetMapping("")
    public String getUserList(Model model){
        model.addAttribute("userList", userService.getAllUser());
        return "ADMIN-HISTORY";
    }
}
