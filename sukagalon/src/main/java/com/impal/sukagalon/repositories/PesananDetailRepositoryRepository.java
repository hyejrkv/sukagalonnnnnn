package com.impal.sukagalon.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.impal.sukagalon.models.PesananDetail;


@Repository
public interface PesananDetailRepositoryRepository extends JpaRepository<PesananDetail, Integer> {

}
