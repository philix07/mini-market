package com.felix.basic_projects.mini_market.exception.transaction;

// Thrown when a specific transaction is not found.
public class TransactionNotFoundException extends RuntimeException {
  public TransactionNotFoundException(String message) {
    super(message);
  }
}
