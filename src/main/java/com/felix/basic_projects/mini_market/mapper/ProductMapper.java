package com.felix.basic_projects.mini_market.mapper;

import com.felix.basic_projects.mini_market.model.dto.response.ProductResponseDTO;
import com.felix.basic_projects.mini_market.model.entity.Product;
import org.springframework.stereotype.Service;

@Service
public class ProductMapper {

  /*
    ProductResponseDTO's field
      private Long id;
      private String name;
      private ProductCategory category;
      private String barcode;
      private double price;
      private int stockQuantity;
  */
  public ProductResponseDTO mapEntityToResponseDTO(Product product) {
    return ProductResponseDTO.builder()
      .id(product.getId())
      .name(product.getName())
      .category(product.getCategory())
      .barcode(product.getBarcode())
      .price(product.getPrice())
      .stockQuantity(product.getStockQuantity())
      .build();
  }

}
