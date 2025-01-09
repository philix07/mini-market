package com.felix.basic_projects.mini_market.exception.product;

// Thrown when a product’s details (e.g., price, stock) are invalid.
public class InvalidProductDetailsException extends RuntimeException {
  public InvalidProductDetailsException(String message) {
    super(message);
  }
}
