package com.felix.basic_projects.mini_market.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

// Represents individual items within a transaction.
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"transaction_id", "product_id"}))
public class TransactionItem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id; // Primary Key

  @JsonBackReference // Child side, prevents recursive serialization
  @ManyToOne(fetch = FetchType.EAGER, optional = false) // Transaction is mandatory
  @JoinColumn(name = "transaction_id", nullable = false)
  private Transaction transaction;

  // @ManyToOne indicates that many TransactionItem entities can reference the same Product.
  // This is what you need because a Product (e.g., Milk) can be sold in multiple transactions
  // (Buyer A and Buyer B both bought Milk).
  @ManyToOne(fetch = FetchType.EAGER, optional = false) // A product can appear in multiple transactions
  @JoinColumn(name = "product_id", nullable = false)
  private Product product;

  @Min(value = 1, message = "Quantity must be at least 1")
  private int quantity; // Quantity purchased

  @Positive(message = "Total should be positive")
  private double total; // Total price (quantity * price)

  // Why do we need price per unit when we already linked it with Product Entity?
  // because the price from the Product Entity might change one day, but if we store
  // the unit price separately, we can keep the initial transaction price.
  @PositiveOrZero(message = "Price should be >= 0")
  private double price; // Price per unit

  public double calculateTotal() {
    this.total = this.price * this.quantity;
    return total;
  }

  public TransactionItem() {}

  public TransactionItem(Long id, Transaction transaction, Product product, int quantity, double total, double price) {
    this.id = id;
    this.transaction = transaction;
    this.product = product;
    this.quantity = quantity;
    this.total = total;
    this.price = price;
  }

  public TransactionItem(Transaction transaction, int quantity, double price, Product product) {
    this.transaction = transaction;
    this.quantity = quantity;
    this.price = price;
    this.product = product;
    this.total = calculateTotal();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Transaction getTransaction() {
    return transaction;
  }

  public void setTransaction(Transaction transaction) {
    this.transaction = transaction;
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
    this.total = calculateTotal();
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
    this.total = calculateTotal();
  }

  public double getTotal() {
    return total;
  }

  @Override
  public String
  toString() {
    return "TransactionItem{" +
      "id=" + id +
      ", transaction=" + transaction +
      ", product=" + product +
      ", quantity=" + quantity +
      ", total=" + total +
      ", price=" + price +
      '}';
  }
}
