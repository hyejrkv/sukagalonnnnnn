package com.impal.sukagalon.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.impal.sukagalon.models.Pesanan;

@Repository
public interface PesananRepository extends JpaRepository<Pesanan, Integer> {
    List<Pesanan> findByUserIdUser(int userId);
}
