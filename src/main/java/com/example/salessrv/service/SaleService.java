package com.example.salessrv.service;

import java.util.List;
import java.util.Optional;

import com.example.salessrv.model.Sale;

public interface SaleService {
  List<Sale> getAllSales();

  Optional<Sale> getSaleById(Long id);

  Sale createSale(Sale sale);

  Sale updateSale(Long id, Sale sale);

  void deleteSale(Long id);
}
