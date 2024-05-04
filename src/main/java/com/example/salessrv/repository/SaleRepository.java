package com.example.salessrv.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.salessrv.model.Sale;

public interface SaleRepository extends JpaRepository<Sale, Long> {
  
}
