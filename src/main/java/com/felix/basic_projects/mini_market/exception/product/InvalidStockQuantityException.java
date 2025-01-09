package com.felix.basic_projects.mini_market.exception.product;

// Thrown when a product is out of stock, and a transaction is attempted.
public class InvalidStockQuantityException extends RuntimeException {
  public InvalidStockQuantityException(String message) {
    super(message);
  }
}
