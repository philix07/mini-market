package com.felix.basic_projects.mini_market.model;

import com.felix.basic_projects.mini_market.model.enums.ProductCategory;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;


// Represents items sold in the mini market.
@Entity
public class Product {

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

  public Product() {}

  public Product(Long id, String name, ProductCategory category, String barcode, double price, int stockQuantity) {
    this.id = id;
    this.name = name;
    this.category = category;
    this.barcode = barcode;
    this.price = price;
    this.stockQuantity = stockQuantity;
  }

  public Product(String name, ProductCategory category, String barcode, double price, int stockQuantity) {
    this.name = name;
    this.category = category;
    this.barcode = barcode;
    this.price = price;
    this.stockQuantity = stockQuantity;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public ProductCategory getCategory() {
    return category;
  }

  public void setCategory(ProductCategory category) {
    this.category = category;
  }

  public String getBarcode() {
    return barcode;
  }

  public void setBarcode(String barcode) {
    this.barcode = barcode;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public int getStockQuantity() {
    return stockQuantity;
  }

  public void setStockQuantity(int stockQuantity) {
    this.stockQuantity = stockQuantity;
  }

  @Override
  public String toString() {
    return "Product{" +
      "id=" + id +
      ", name='" + name + '\'' +
      ", category='" + category + '\'' +
      ", barcode='" + barcode + '\'' +
      ", price=" + price +
      ", stockQuantity=" + stockQuantity +
      '}';
  }
}
