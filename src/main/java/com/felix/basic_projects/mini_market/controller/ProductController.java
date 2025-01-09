package com.felix.basic_projects.mini_market.controller;

import com.felix.basic_projects.mini_market.model.Product;
import com.felix.basic_projects.mini_market.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

// handles "Product" and "StockEntry" model class
@RestController
@RequestMapping("/v1")
public class ProductController {

  @Autowired
  private ProductService service;

  @GetMapping("products")
  public ResponseEntity<List<Product>> retrieveAllProduct() {
    List<Product> products = service.retrieveAllProduct();

    return ResponseEntity.ok(products);
  }

  @GetMapping("products/{id}")
  public ResponseEntity<Product> findProductById(@PathVariable Long id) {
    Product product = service.findProductById(id);

    return ResponseEntity.ok(product);
  }

  @PostMapping("products/batch")
  public ResponseEntity<List<Product>> saveAllProduct(@Valid @RequestBody List<Product> products) {
    List<Product> savedProducts = service.saveAllProduct(products);

    // Map the products to their respective URIs
    List<URI> locations = savedProducts.stream().map(
      product -> ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(product.getId())
        .toUri()
      ).toList();

    return ResponseEntity.created(locations.get(0)).body(savedProducts);
  }

  @PostMapping("products")
  public ResponseEntity<Product> saveProduct(@Valid @RequestBody Product product) {
    Product savedProduct = service.saveProduct(product);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
      .path("/{id}")
      .buildAndExpand(product.getId())
      .toUri();

    return ResponseEntity.created(location).body(product);
  }

  @DeleteMapping("products/{id}")
  public ResponseEntity<Product> deleteProductById(@PathVariable Long id) {
    Product deletedProduct = service.deleteProductById(id);
    return ResponseEntity.ok(deletedProduct);
  }

  @PatchMapping("products/{id}")
  public ResponseEntity<Product> updateProductById(@PathVariable Long id, @Valid @RequestBody Product product) {
    Product updatedProduct = service.updateProductById(id, product);
    return ResponseEntity.ok(updatedProduct);
  }
}


