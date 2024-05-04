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

import com.example.salessrv.Product;
import com.example.salessrv.exception.ResourceNotFoundException;
import com.example.salessrv.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {
  private static final Logger log = LoggerFactory.getLogger(SaleController.class);

  @Autowired
  private ProductService productService;

  @GetMapping
  public CollectionModel<EntityModel<Product>> getAllProducts() {
    log.info("GET /product");
    log.info("Retornando todas los productos");
    List<Product> products = productService.getAllProducts();
    List<EntityModel<Product>> productResources = products.stream()
        .map(product -> EntityModel.of(product,
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(this.getClass()).getProductById(product.getId()))
                .withSelfRel()))
        .collect(Collectors.toList());

    WebMvcLinkBuilder linkTo = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllProducts());
    CollectionModel<EntityModel<Product>> resources = CollectionModel.of(productResources,
        linkTo.withRel("products"));
    return resources;
  }

  @GetMapping("/{id}")
  public EntityModel<Product> getProductById(@PathVariable Long id) {
    log.info("GET /product/" + id);
    log.info("Retornando el producto de id:" + id);
    Optional<Product> product = productService.getProductById(id);
    if (product.isPresent()) {
      return EntityModel.of(product.get(),
          WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getProductById(id))
              .withSelfRel(),
          WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllProducts())
              .withRel("all-products"));
    } else {
      throw new ResourceNotFoundException("The product id doesnt exists");
    }
  }

  @PostMapping
  public EntityModel<Product> createProduct(@RequestBody Product product) {
    log.info("POST /product");
    log.info("Creando un producto");
    Product createdProduct = productService.createProduct(product);
    return EntityModel.of(createdProduct,
        WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(this.getClass()).getProductById(createdProduct.getId()))
            .withSelfRel(),
        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllProducts())
            .withRel("all-products"));
  }

  @PutMapping("/{id}")
  public EntityModel<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
    log.info("PUT /product/" + id);
    log.info("Actualizando el producto de id:" + id);
    Product updatedProduct = productService.updateProduct(id, product);
    return EntityModel.of(updatedProduct,
        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getProductById(id))
            .withSelfRel(),
        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllProducts())
            .withRel("all-products"));
  }

  @DeleteMapping("/{id}")
  public void deleteProduct(@PathVariable Long id) {
    log.info("DELETE /product/" + id);
    log.info("Eliminado el producto de id:" + id);
    productService.deleteProduct(id);
  }
}
