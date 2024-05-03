package com.example.salessrv.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.salessrv.exception.ResourceNotFoundException;
import com.example.salessrv.model.Sale;
import com.example.salessrv.service.SaleService;

@RestController
@RequestMapping("/sales")
public class SaleController {
  private static final Logger log = LoggerFactory.getLogger(SaleController.class);

  @Autowired
  private SaleService saleService;

  @GetMapping
  public CollectionModel<EntityModel<Sale>> getAllSales() {
    log.info("GET /sales");
    log.info("Retornando todas las ventas");
    List<Sale> sales = saleService.getAllSales();
    List<EntityModel<Sale>> saleResources = sales.stream()
        .map(sale -> EntityModel.of(sale,
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(this.getClass()).getSaleById(sale.getId()))
                .withSelfRel()))
        .collect(Collectors.toList());

    WebMvcLinkBuilder linkTo = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllSales());
    CollectionModel<EntityModel<Sale>> resources = CollectionModel.of(saleResources,
        linkTo.withRel("sales"));
    return resources;
  }

  @GetMapping("/{id}")
  public EntityModel<Sale> getSaleById(@PathVariable Long id) {
    log.info("GET /sales/" + id);
    log.info("Retornando la venta de id:" + id);
    Optional<Sale> sale = saleService.getSaleById(id);
    if (sale.isPresent()) {
      return EntityModel.of(sale.get(),
          WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getSaleById(id))
              .withSelfRel(),
          WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllSales())
              .withRel("all-sales"));
    } else {
      throw new ResourceNotFoundException("The sale id doesnt exists");
    }
  }

  @PostMapping
  public EntityModel<Sale> createSale(@RequestBody Sale sale) {
    log.info("POST /sales");
    log.info("Creando una venta");
    Sale createdSale = saleService.createSale(sale);
    return EntityModel.of(createdSale,
        WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(this.getClass()).getSaleById(createdSale.getId()))
            .withSelfRel(),
        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllSales())
            .withRel("all-sales"));
  }

  @PutMapping("/{id}")
  public EntityModel<Sale> updateSale(@PathVariable Long id, @RequestBody Sale sale) {
    log.info("PUT /sales/" + id);
    log.info("Actualizando la venta de id:" + id);
    Sale updatedSale = saleService.updateSale(id, sale);
    return EntityModel.of(updatedSale,
        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getSaleById(id))
            .withSelfRel(),
        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllSales())
            .withRel("all-sales"));
  }

  @DeleteMapping("/{id}")
  public void deleteSale(@PathVariable Long id) {
    log.info("DELETE /sales/" + id);
    log.info("Eliminado la venta de id:" + id);
    saleService.deleteSale(id);
  }

  @GetMapping("/daily")
  public int getTotalSalesDaily() {
    log.info("GET /sales/daily");
    log.info("Retornando total de ventas diarias");
    return saleService.getTotalSalesDaily();
  }

  @GetMapping("/monthly")
  public int getTotalSalesMonthly() {
    log.info("GET /sales/monthly");
    log.info("Retornando total de ventas mensuales");
    return saleService.getTotalSalesMonthly();
  }

  @GetMapping("/annual")
  public int getTotalSalesAnnual() {
    log.info("GET /sales");
    log.info("Retornando total de ventas anuales");
    return saleService.getTotalSalesAnnual();
  }
}
