package com.impal.sukagalon.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.impal.sukagalon.models.Produk;
import com.impal.sukagalon.services.ProdukService;

@Controller
@RequestMapping("/api/produk")
public class ProdukController {

    @Autowired
    private ProdukService produkService;

    @GetMapping
    @ResponseBody
    public List<Map<String, Integer>> getAllStocks() {
        List<Produk> products = produkService.getAllProduk();
        return products.stream()
            .map(p -> Map.of(
                "idProduk", p.getIdProduk(),
                "stok", p.getStok()
            ))
            .collect(Collectors.toList());
    }

    @PostMapping("/update-stok")
    @ResponseBody
    public ResponseEntity<?> updateStok(@RequestBody Map<String, Integer> request) {
        try {
            int produkId = request.get("produkId");
            int quantity = request.get("quantity");
            produkService.updateStok(produkId, quantity);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
