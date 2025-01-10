package com.felix.basic_projects.mini_market.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDateTime;


// Represents stock updates (e.g., new stock arrivals or adjustments).
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class StockEntry {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id; // Primary Key

  @NotNull
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

}
