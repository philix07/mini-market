package com.felix.basic_projects.mini_market.controller;

import com.felix.basic_projects.mini_market.model.dto.request.CreateProductRequestDTO;
import com.felix.basic_projects.mini_market.model.dto.request.UpdateProductRequestDTO;
import com.felix.basic_projects.mini_market.model.dto.response.ProductResponseDTO;
import com.felix.basic_projects.mini_market.model.entity.Product;
import com.felix.basic_projects.mini_market.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

// handles "Product" and "StockEntry" model class
@RestController
@RequestMapping("/v1")
public class ProductController {

  private final ProductService service;

  public ProductController(ProductService service) {
    this.service = service;
  }

  @GetMapping("products")
  public ResponseEntity<List<ProductResponseDTO>> retrieveAllProduct() {
    return ResponseEntity.ok(service.retrieveAllProduct());
  }

  @GetMapping("products/{id}")
  public ResponseEntity<ProductResponseDTO> findProductById(@PathVariable Long id) {
    return ResponseEntity.ok(service.findProductById(id));
  }

  @PostMapping("products/batch")
  public ResponseEntity<List<ProductResponseDTO>> saveAllProduct(@Valid @RequestBody List<CreateProductRequestDTO> products) {
    List<ProductResponseDTO> savedProducts = service.saveAllProduct(products);

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
  public ResponseEntity<ProductResponseDTO> saveProduct(@Valid @RequestBody CreateProductRequestDTO product) {
    ProductResponseDTO savedProduct = service.saveProduct(product);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
      .path("/{id}")
      .buildAndExpand(savedProduct.getId())
      .toUri();

    return ResponseEntity.created(location).body(savedProduct);
  }

  @DeleteMapping("products/{id}")
  public ResponseEntity<ProductResponseDTO> deleteProductById(@PathVariable Long id) {
    return ResponseEntity.ok(service.deleteProductById(id));
  }

  @PatchMapping("products/{id}")
  public ResponseEntity<ProductResponseDTO> updateProductById(
    @PathVariable Long id,
    @Valid @RequestBody UpdateProductRequestDTO product
  ) {
    return ResponseEntity.ok(service.updateProductById(id, product));
  }
}


