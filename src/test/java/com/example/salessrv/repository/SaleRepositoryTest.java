package com.example.salessrv.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import com.example.salessrv.SalessrvApplication;
import com.example.salessrv.model.Sale;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes=SalessrvApplication.class)
public class SaleRepositoryTest {
  @Autowired
  private SaleRepository saleRepository;

@Test
public void saveSaleTest() {
    Sale sale = new Sale();
    LocalDate date = LocalDate.parse("2004-05-01");
    sale.setSaleDate(date);

    Sale result = saleRepository.save(sale);

    assertNotNull(result.getId());
    assertEquals(date, result.getSaleDate());
}

}
