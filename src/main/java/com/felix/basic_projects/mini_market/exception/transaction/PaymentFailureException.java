package com.felix.basic_projects.mini_market.exception.transaction;

// Thrown when the transaction item details are invalid (e.g., negative quantity or price).
public class PaymentFailureException extends RuntimeException {
  public PaymentFailureException(String message) {
    super(message);
  }
}
