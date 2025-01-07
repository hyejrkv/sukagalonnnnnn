package com.impal.sukagalon.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.impal.sukagalon.models.Pesanan;
import com.impal.sukagalon.repositories.PesananRepository;

@Service
public class PesananService {

    @Autowired
    private PesananRepository pesananRepository;

    public List<Pesanan> getAllPesanan(){
        return pesananRepository.findAll();
    }

    public Optional<Pesanan> getPesananById(int id){
        return pesananRepository.findById(id);
    }

    public void savePesanan(Pesanan pesanan) {
        pesananRepository.save(pesanan);
    }

    public void deletePesanan(int id) {
        pesananRepository.deleteById(id);
    }

    public List<Pesanan> getPesananByUserId(int userId) {
        return pesananRepository.findByUserIdUser(userId);
    }
}
