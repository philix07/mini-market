package com.felix.basic_projects.mini_market.exception.customer;

// Thrown when a customer with a specific ID or name is not found.
public class CustomerNotFoundException extends RuntimeException {
  public CustomerNotFoundException(String message) {
    super(message);
  }
}
