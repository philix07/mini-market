package com.felix.basic_projects.mini_market.exception.product;

// Thrown when trying to add a product that already exists.
public class DuplicateProductException extends RuntimeException {
  public DuplicateProductException(String message) {
    super(message);
  }
}
