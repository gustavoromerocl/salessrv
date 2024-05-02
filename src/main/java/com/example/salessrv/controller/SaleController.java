package com.example.salessrv.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.salessrv.model.Sale;
import com.example.salessrv.service.SaleService;

@RestController
@RequestMapping("/sales")
public class SaleController {
  private static final Logger log = LoggerFactory.getLogger(SaleController.class);

  @Autowired
  private SaleService saleService;

  @GetMapping
  public List<Sale> getAllSales() {
    log.info("GET /sales");
    log.info("Retornando todas las ventas");
    return saleService.getAllSales();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Sale> getSaleById(@PathVariable Long id) {
    Optional<Sale> saleOptional = saleService.getSaleById(id);
    log.info("GET /sales/" + id);
    log.info("Retornando la venta de id:" + id);
    return saleOptional.map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping
  public Sale createSale(@RequestBody Sale sale) {
    log.info("POST /sales");
    log.info("Creando una venta");
    return saleService.createSale(sale);
  }

  @PutMapping("/{id}")
  public Sale updateSale(@PathVariable Long id, @RequestBody Sale sale) {
    log.info("PUT /sales/" + id);
    log.info("Actualizando la venta de id:" + id);
    return saleService.updateSale(id, sale);
  }

  @DeleteMapping("/{id}")
  public void deleteSale(@PathVariable Long id) {
    log.info("DELETE /sales/" + id);
    log.info("Eliminado la venta de id:" + id);
    saleService.deleteSale(id);
  }
}
