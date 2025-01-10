package com.impal.sukagalon.controller;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.impal.sukagalon.models.Pesanan;
import com.impal.sukagalon.models.PesananDetail;
import com.impal.sukagalon.models.PesananDetailDTO;
import com.impal.sukagalon.models.PesananFormDTO;
import com.impal.sukagalon.models.Produk;
import com.impal.sukagalon.models.User;
import com.impal.sukagalon.services.PesananService;
import com.impal.sukagalon.services.ProdukService;
//import com.impal.sukagalon.services.ProdukService;
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

    @GetMapping("/addPesanan")
    public String create(@RequestParam int idUser, Model model, HttpSession session) {
        User currentUser = (User) session.getAttribute("user");
        if (currentUser !=null && !"ADMIN".equalsIgnoreCase(currentUser.getRole())) {
            return "redirect:/accessDenied";
        }
        model.addAttribute("pesananForm", new PesananFormDTO());
        model.addAttribute("user", userService.findById(idUser));
        model.addAttribute("pesananDetail", new PesananDetail());
        model.addAttribute("produkList", produkService.getAllProduk());
        return "FORM-INPUT";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam int id, Model model, HttpSession session) {
        User currentUser = (User) session.getAttribute("user");
        if (currentUser !=null && !"ADMIN".equalsIgnoreCase(currentUser.getRole())) {
            return "redirect:/accessDenied";
        }
        model.addAttribute("pesanan", pesananService.getPesananById(id).orElseThrow());
        model.addAttribute("user", userService.getAllUser());
        return "FORM-STATUS";
    }

    @PostMapping("/store")
    public String store(@ModelAttribute PesananFormDTO pesananForm, HttpSession session) {
        User currentUser = (User) session.getAttribute("user");
        if (currentUser !=null && !"ADMIN".equalsIgnoreCase(currentUser.getRole())) {
            return "redirect:/accessDenied";
        }

        Pesanan pesanan = new Pesanan();
        pesanan.setUser(pesananForm.getUser());
        pesanan.setStatus(pesananForm.getStatus());
        pesanan.setTanggalPesanan(LocalDateTime.now());
        
        double totalHarga = 0;
        double subtot = 0;
        for (PesananDetailDTO detailDTO : pesananForm.getPesananDetails()) {
            Optional<Produk> produkOpt = produkService.getProdukById(detailDTO.getProdukId());
            Produk produk = produkOpt.orElse(new Produk());

            PesananDetail detail = new PesananDetail();
            detail.setProduk(produk);
            detail.setKuantitas(detailDTO.getKuantitas());
            subtot=produk.getHarga() * detailDTO.getKuantitas();
            detail.setSubTotal(subtot);
            
            totalHarga += detail.getSubTotal();
            pesanan.addPesananDetail(detail);
        }
        
        pesanan.setTotalHarga(totalHarga);
        pesananService.savePesanan(pesanan);
        
        return "redirect:/AdminHistory";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Pesanan pesanan, HttpSession session) {
        User currentUser = (User) session.getAttribute("user");
        if (currentUser !=null && !"ADMIN".equalsIgnoreCase(currentUser.getRole())) {
            return "redirect:/accessDenied";
        }
        Pesanan existingPesanan = pesananService.getPesananById(pesanan.getId()).orElseThrow();
        pesanan.setTotalHarga(existingPesanan.getTotalHarga());
        pesanan.setTanggalPesanan(existingPesanan.getTanggalPesanan());
        pesanan.setPesananDetails(existingPesanan.getPesananDetails());
        pesananService.savePesanan(pesanan);
        return "redirect:/AdminHistory";
        
    }
}
