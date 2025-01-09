package com.impal.sukagalon.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.impal.sukagalon.models.Pesanan;
import com.impal.sukagalon.models.Produk;
import com.impal.sukagalon.repositories.ProdukRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProdukService {

    @Autowired
    private ProdukRepository produkRepository;

    /*public Produk updateStok(int idProduk, int jumlahStokBaru) {
        Optional<Produk> produkOpt = produkRepository.findById(idProduk);
        if (produkOpt.isPresent()) {
            Produk produk = produkOpt.get();
            produk.setStok(jumlahStokBaru);
            return produkRepository.save(produk);
        } else {
            throw new RuntimeException("Produk dengan ID " + idProduk + " tidak ditemukan.");
        }
    }*/

    public List<Produk> getAllProduk() {
        return produkRepository.findAll();
    }

    public int getStokByID(int idProduk) {
        Optional<Produk> produkOpt = produkRepository.findById(idProduk);
        if (produkOpt.isPresent()) {
            return produkOpt.get().getStok();
        } else {
            throw new RuntimeException("Produk dengan ID " + idProduk + " tidak ditemukan.");
        }
    }

    public Optional<Produk> getProdukById(int id){
        return produkRepository.findById(id);
    }

    public void saveProduk(Produk produk) {
        produkRepository.save(produk);
    }

    public void deleteProduk(int id) {
        produkRepository.deleteById(id);
    }
}