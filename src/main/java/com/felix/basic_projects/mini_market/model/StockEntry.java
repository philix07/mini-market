package com.felix.basic_projects.mini_market.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

// Represents stock updates (e.g., new stock arrivals or adjustments).
@Entity
public class StockEntry {

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

  public StockEntry() {}

  public StockEntry(Long id, LocalDateTime createdAt, Product product, int quantity, double totalPrice) {
    this.id = id;
    this.createdAt = createdAt;
    this.product = product;
    this.quantity = quantity;
    this.totalPrice = totalPrice;
  }

  public StockEntry(Product product, int quantity, double totalPrice) {
    this.product = product;
    this.quantity = quantity;
    this.totalPrice = totalPrice;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public double getTotalPrice() {
    return totalPrice;
  }

  public void setTotalPrice(double totalPrice) {
    this.totalPrice = totalPrice;
  }

  @Override
  public String toString() {
    return "StockEntry{" +
      "id=" + id +
      ", createdAt=" + createdAt +
      ", product=" + product +
      ", quantity=" + quantity +
      ", totalPrice=" + totalPrice +
      '}';
  }
}
