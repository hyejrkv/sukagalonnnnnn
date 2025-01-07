package com.impal.sukagalon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

//import com.impal.sukagalon.services.PesananService;
import com.impal.sukagalon.services.ProdukService;

@Controller
@RequestMapping("/Belanja")
public class BelanjaController {
    
    @Autowired
    private ProdukService produkService;

    @GetMapping("")
    public String getStok(Model model){
        model.addAttribute("stok1", produkService.getStokByID(1));
        model.addAttribute("stok2", produkService.getStokByID(2));
        model.addAttribute("stok3", produkService.getStokByID(3));
        model.addAttribute("stok4", produkService.getStokByID(4));
        model.addAttribute("stok5", produkService.getStokByID(5));
        model.addAttribute("stok6", produkService.getStokByID(6));

        return "SG_BELANJA";
    }


}

