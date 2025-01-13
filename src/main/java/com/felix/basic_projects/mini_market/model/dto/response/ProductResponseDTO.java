package com.felix.basic_projects.mini_market.model.dto.response;

import com.felix.basic_projects.mini_market.model.entity.enums.ProductCategory;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponseDTO {
  private Long id;
  private String name;
  private ProductCategory category;
  private String barcode;
  private double price;
  private int stockQuantity;
}
