package com.impal.sukagalon.controller;

//import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

//import com.impal.sukagalon.models.Pesanan;
import com.impal.sukagalon.models.Produk;
import com.impal.sukagalon.models.User;
import com.impal.sukagalon.services.ProdukService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/AdminProduk")
public class AdminProdukController {

    @Autowired
    private ProdukService produkService;

    @GetMapping("")
    public String getProdukList(Model model, HttpSession session){
        User currentUser = (User) session.getAttribute("user");
        if (currentUser==null || !"ADMIN".equals(currentUser.getRole())){
            return "redirect:/accessDenied";
        }
        model.addAttribute("produkList", produkService.getAllProduk());
        return "ADM-KELOLA-PRODUK";
    }
    
    @GetMapping("/edit")
    public String edit(@RequestParam int idProduk, Model model, HttpSession session) {
        User currentUser = (User) session.getAttribute("user");
        if (currentUser !=null && !"ADMIN".equalsIgnoreCase(currentUser.getRole())) {
            return "redirect:/accessDenied";
        }
        model.addAttribute("produk", produkService.getProdukById(idProduk).orElseThrow());
        return "FORM-STOK";
    }

    @PostMapping("/store")
    public String store(@ModelAttribute Produk produk, HttpSession session, RedirectAttributes redirectAttributes) {
        User currentUser = (User) session.getAttribute("user");
        if (currentUser !=null && !"ADMIN".equalsIgnoreCase(currentUser.getRole())) {
            return "redirect:/accessDenied";
        }
        produkService.saveProduk(produk);
        return "redirect:/AdminProduk";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Produk produk, HttpSession session, RedirectAttributes redirectAttributes) {
        User currentUser = (User) session.getAttribute("user");
        if (currentUser !=null && !"ADMIN".equalsIgnoreCase(currentUser.getRole())) {
            return "redirect:/accessDenied";
        }
        Produk existingProduk = produkService.getProdukById(produk.getIdProduk()).orElseThrow();
        produk.setHarga(existingProduk.getHarga());
        produk.setMerk(existingProduk.getMerk());
        if (produk.getStok()<0) {
            produk.setStok(existingProduk.getStok());
            redirectAttributes.addFlashAttribute("error", "Stok tidak boleh kurang dari 0!");
            return "redirect:/AdminProduk/edit?idProduk=" + produk.getIdProduk();
        }
        produkService.saveProduk(produk);
        return "redirect:/AdminProduk";
    }
}

