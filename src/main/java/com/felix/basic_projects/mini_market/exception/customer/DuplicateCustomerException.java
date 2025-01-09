package com.felix.basic_projects.mini_market.exception.customer;

// Thrown when trying to add a customer with existing details.
public class DuplicateCustomerException extends RuntimeException {
  public DuplicateCustomerException(String message) {
    super(message);
  }
}
