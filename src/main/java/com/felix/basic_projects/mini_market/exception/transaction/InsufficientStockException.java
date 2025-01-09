package com.felix.basic_projects.mini_market.exception.transaction;

// Thrown when the stock is insufficient to complete the transaction.
public class InsufficientStockException extends RuntimeException {
  public InsufficientStockException(String message) {
    super(message);
  }
}
