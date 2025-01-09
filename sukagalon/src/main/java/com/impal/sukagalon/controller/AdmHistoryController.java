package com.impal.sukagalon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.impal.sukagalon.models.Pesanan;
import com.impal.sukagalon.models.User;
import com.impal.sukagalon.services.PesananService;
import com.impal.sukagalon.services.ProdukService;
//import com.impal.sukagalon.services.PesananService;
import com.impal.sukagalon.services.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/AdminHistory")
public class AdmHistoryController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private PesananService pesananService;

    @Autowired
    private ProdukService produkService;

    @GetMapping("")
    public String getUserList(Model model, HttpSession session){
        User currentUser = (User) session.getAttribute("user");
        if (currentUser==null || !"ADMIN".equals(currentUser.getRole())){
            return "redirect:/accessDenied";
        }
        model.addAttribute("userList", userService.getAllUser());
        model.addAttribute("pesananList", pesananService.getAllPesanan());
        return "ADMIN-HISTORY";
    }

    @GetMapping("/filterPesanan")
    public String filterPesanan(@RequestParam int idUser, Model model, HttpSession session){
        User currentUser = (User) session.getAttribute("user");
        if (currentUser==null || !"ADMIN".equals(currentUser.getRole())){
            return "redirect:/accessDenied";
        }
        model.addAttribute("userList", userService.getAllUser());
        model.addAttribute("pesananList", pesananService.getPesananByUserId(idUser));
        return "ADMIN-HISTORY";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam int id, Model model, HttpSession session) {
        
        model.addAttribute("pesanan", pesananService.getPesananById(id).orElseThrow());
        return "FORM-STATUS";
    }

    @PostMapping("/store")
    public String store(@ModelAttribute Pesanan pesanan, HttpSession session) {
        
        pesananService.savePesanan(pesanan);
        return "redirect:/AdminHistory";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Pesanan pesanan, HttpSession session) {
        
        Pesanan existingPenjualan = pesananService.getPesananById(pesanan.getId()).orElseThrow();
        pesanan.setTotalHarga(existingPenjualan.getTotalHarga());
        pesanan.setTanggalPesanan(existingPenjualan.getTanggalPesanan());
        pesanan.setUser(existingPenjualan.getUser());
        pesanan.setPesananDetails(existingPenjualan.getPesananDetails());
        pesananService.savePesanan(pesanan);
        return "redirect:/AdminHistory";
    }
}
