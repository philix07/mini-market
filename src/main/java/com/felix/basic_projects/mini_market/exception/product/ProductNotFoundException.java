package com.felix.basic_projects.mini_market.exception.product;

// Thrown when a product with a given ID or name is not found in the database.
public class ProductNotFoundException extends RuntimeException {
  public ProductNotFoundException(String message) {
    super(message);
  }
}
