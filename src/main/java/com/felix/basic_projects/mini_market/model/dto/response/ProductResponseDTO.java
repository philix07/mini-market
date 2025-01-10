package com.felix.basic_projects.mini_market.model.dto.response;

import com.felix.basic_projects.mini_market.model.entity.enums.ProductCategory;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;


@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDTO {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id; // Primary Key

  @NotEmpty(message = "Password cannot be empty or null")
  @Size(min = 4, message = "Product name should have at least 4 character")
  private String name; // Product name

  @NotNull(message = "Product category cannot be nulled")
  @Enumerated(EnumType.STRING)
  private ProductCategory category; // e.g., "Groceries", "Beverages"

  @NotEmpty(message = "Product barcode cannot be empty or null")
  private String barcode; // Barcode for scanning

  @Positive(message = "Product price should be greater than 0")
  @NotNull(message = "Product price cannot be empty or null")
  private double price; // Selling price per unit


  @PositiveOrZero(message = "Stock quantity should be greater or equals to 0")
  @NotNull(message = "Stock quantity cannot be empty or null")
  private int stockQuantity; // Available stock

  public ProductResponseDTO(String name, ProductCategory category, String barcode, double price, int stockQuantity) {
    this.name = name;
    this.category = category;
    this.barcode = barcode;
    this.price = price;
    this.stockQuantity = stockQuantity;
  }

}
