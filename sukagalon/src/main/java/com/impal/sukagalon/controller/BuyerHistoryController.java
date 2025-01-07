package com.impal.sukagalon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/BuyerHistory")
public class BuyerHistoryController {
    
    @GetMapping("")
    public String landBuyerHist(){
        return "SG_HISTORY";
    }

    
}
