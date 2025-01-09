package com.felix.basic_projects.mini_market.exception.customer;

// Thrown when the provided customer details are invalid (e.g., missing contact info).
public class InvalidCustomerDetailsException extends RuntimeException {
  public InvalidCustomerDetailsException(String message) {
    super(message);
  }
}
