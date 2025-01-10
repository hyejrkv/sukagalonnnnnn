package com.impal.sukagalon.repositories;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.impal.sukagalon.models.Produk;

@Repository
public interface ProdukRepository extends JpaRepository<Produk, Integer> {
    Produk findByMerk(String merk);
}