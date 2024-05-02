package com.example.salessrv.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.salessrv.model.Sale;
import com.example.salessrv.repository.SaleRepository;

@Service
public class SaleServiceImpl implements SaleService {
  @Autowired
  private SaleRepository saleRepository;

  @Override
  public List<Sale> getAllSales() {
    return saleRepository.findAll();
  }

  @Override
  public Optional<Sale> getSaleById(Long id) {
    return saleRepository.findById(id);
  }

  @Override
  public Sale createSale(Sale sale) {
    return saleRepository.save(sale);
  }

  @Override
  public Sale updateSale(Long id, Sale sale) {
    if (saleRepository.existsById(id)) {
      sale.setId(id);
      return saleRepository.save(sale);
    } else {
      return null;
    }
  }

  @Override
  public void deleteSale(Long id) {
    saleRepository.deleteById(id);
  }
}