package com.example.salessrv.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.salessrv.Product;
import com.example.salessrv.model.Sale;
import com.example.salessrv.repository.SaleRepository;

@ExtendWith(MockitoExtension.class)
public class SaleServiceTest {
    @InjectMocks
    private SaleServiceImpl saleService;

    @Mock
    private SaleRepository saleRepositoryMock;

    @Test
    public void saveSaleTest() {
        Sale sale = new Sale();
        sale.setSaleDate(LocalDate.parse("2004-05-01"));

        when(saleRepositoryMock.save(any())).thenReturn(sale);

        Sale result = saleService.createSale(sale);

        assertEquals(LocalDate.parse("2004-05-01"), result.getSaleDate());
    }

    @Test
    public void testGetTotalSalesDaily() {
        LocalDate today = LocalDate.now();
        List<Sale> sales = new ArrayList<>();
        Sale sale = new Sale();
        sale.setSaleDate(today);
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setQuantity(2);
        product.setPrice(10);
        products.add(product);
        sale.setProducts(products);
        sales.add(sale);

        when(saleRepositoryMock.findAll()).thenReturn(sales);

        int expectedTotal = 2 * 10; 
        int actualTotal = saleService.getTotalSalesDaily();
        assertEquals(expectedTotal, actualTotal);
    }
}
