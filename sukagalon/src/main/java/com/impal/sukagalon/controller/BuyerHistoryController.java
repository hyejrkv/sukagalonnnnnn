package com.impal.sukagalon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.impal.sukagalon.models.User;
import com.impal.sukagalon.services.PesananService;
import com.impal.sukagalon.services.ProdukService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/BuyerHistory")
public class BuyerHistoryController {
    
    @Autowired
    private PesananService pesananService;

    @Autowired
    private ProdukService produkService;

    @GetMapping("")
    public String landBuyerHist(Model model, HttpSession session){
        User currentUser = (User) session.getAttribute("user");
        if (currentUser==null) {
            return "SG_HISTORY";
        }
        model.addAttribute("history", pesananService.getPesananByUserId(currentUser.getIdUser()));
        return "SG_HISTORY";
    }

}
