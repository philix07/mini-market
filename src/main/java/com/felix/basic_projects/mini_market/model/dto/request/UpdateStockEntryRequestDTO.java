package com.felix.basic_projects.mini_market.model.dto.request;

import com.felix.basic_projects.mini_market.model.entity.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

// Represents stock updates (e.g., new stock arrivals or adjustments).
public class UpdateStockEntryRequestDTO {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id; // Primary Key

  private LocalDateTime createdAt; // Date of stock addition

  @ManyToOne(fetch = FetchType.EAGER) // Many StockEntries can be associated with one Product
  @NotNull(message = "Product id must be filled")
  private Product product; // Foreign Key to Product

  @NotNull(message = "Quantity field value must be filled")
  @Positive(message = "Quantity value added must be positive")
  private int quantity; // Quantity added

  @NotNull(message = "totalPrice field value must be filled")
  @Positive(message = "totalPrice value must be positive")
  private double totalPrice; // Total price cost for single product

  // This ensures that even if the POST payload does not include createdAt,
  // it will be initialized at the database level.
  @PrePersist
  protected void onCreate() {
    if (createdAt == null) {
      this.createdAt = LocalDateTime.now();
    }
  }

}
