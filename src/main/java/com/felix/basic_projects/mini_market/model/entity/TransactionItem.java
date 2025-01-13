package com.felix.basic_projects.mini_market.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

// Represents individual items within a transaction.
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"transaction_id", "product_id"}))
public class TransactionItem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Setter
  private Long id; // Primary Key

  @JsonBackReference // Child side, prevents recursive serialization
  @ManyToOne(fetch = FetchType.EAGER, optional = false) // Transaction is mandatory
  @JoinColumn(name = "transaction_id", nullable = false)
  @Setter
  private Transaction transaction;

  // @ManyToOne indicates that many TransactionItem entities can reference the same Product.
  // This is what you need because a Product (e.g., Milk) can be sold in multiple transactions
  // (Buyer A and Buyer B both bought Milk).
  @ManyToOne(fetch = FetchType.EAGER, optional = false) // A product can appear in multiple transactions
  @JoinColumn(name = "product_id", nullable = false)
  @Setter
  private Product product;

  @Min(value = 1, message = "Quantity must be at least 1")
  private int quantity; // Quantity purchased

  // Why do we need price per unit when we already linked it with Product Entity?
  // because the price from the Product Entity might change one day, but if we store
  // the unit price separately, we can keep the initial transaction price.
  @PositiveOrZero(message = "Price should be >= 0")
  private double price; // Price per unit

  @Positive(message = "Total should be positive")
  private double total; // Total price (quantity * price)

  public double calculateTotal() {
    this.total = this.price * this.quantity;
    return total;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
    this.total = calculateTotal();
  }

  public void setPrice(double price) {
    this.price = price;
    this.total = calculateTotal();
  }

}
