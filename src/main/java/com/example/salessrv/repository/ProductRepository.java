package com.example.salessrv.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.salessrv.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{
  
}
