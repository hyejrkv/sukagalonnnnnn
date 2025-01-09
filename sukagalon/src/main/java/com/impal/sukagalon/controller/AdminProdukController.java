package com.impal.sukagalon.controller;

//import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/{id}/stok")
    public Produk updateStok(@PathVariable("id") int idProduk, @RequestParam("stok") int jumlahStokBaru, HttpSession session) {

        return produkService.updateStok(idProduk, jumlahStokBaru);
    }
}

