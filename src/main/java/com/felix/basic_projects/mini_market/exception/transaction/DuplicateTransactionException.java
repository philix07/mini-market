package com.felix.basic_projects.mini_market.exception.transaction;

// Thrown when a specific transaction is not found.
public class DuplicateTransactionException extends RuntimeException {
  public DuplicateTransactionException(String message) {
    super(message);
  }
}
