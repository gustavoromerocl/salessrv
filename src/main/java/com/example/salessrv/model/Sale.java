package com.example.salessrv.model;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.example.salessrv.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "sale")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Sale {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "sale_date")
  private LocalDate saleDate;

  @OneToMany(mappedBy = "sale")
  @JsonIgnore
  private List<Product> products;

  public List<ProductDTO> getProductDTOs() {
    if (products != null) {
      return products.stream()
          .map(product -> new ProductDTO(product.getName(), product.getQuantity(), product.getPrice()))
          .collect(Collectors.toList());
    }
    return null;
  }
}
