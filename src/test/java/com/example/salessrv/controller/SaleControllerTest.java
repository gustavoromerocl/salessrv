package com.example.salessrv.controller;

import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.salessrv.SalessrvApplication;
import com.example.salessrv.model.Sale;
import com.example.salessrv.service.SaleServiceImpl;


@WebMvcTest(SaleController.class)
@ContextConfiguration(classes=SalessrvApplication.class)
public class SaleControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private SaleServiceImpl saleServiceMock;

  @Test
  public void getAllTest() throws Exception {
    Sale sale1 = new Sale();
    sale1.setSaleDate(LocalDate.parse("2004-05-01"));
    Sale sale2 = new Sale();
    sale2.setSaleDate(LocalDate.parse("2004-05-02"));

    List<Sale> sales = Arrays.asList(sale1, sale2);
    when(saleServiceMock.getAllSales()).thenReturn(sales);

    mockMvc.perform(MockMvcRequestBuilders.get("/sales"))
      .andExpect(MockMvcResultMatchers.status().isOk());
  }
}
