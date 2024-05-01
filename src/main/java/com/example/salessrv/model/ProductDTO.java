package com.example.salessrv.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProductDTO {
  private String name;
  private int quantity;
  private int price;
}
