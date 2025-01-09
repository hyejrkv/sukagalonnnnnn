package com.impal.sukagalon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("accessDenied")
public class accessDeniedController {
    
    @GetMapping("")
    public String accessDenied(){
        return "index";
    }
}
