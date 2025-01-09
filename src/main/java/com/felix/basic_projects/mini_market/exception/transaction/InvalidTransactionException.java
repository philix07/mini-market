package com.felix.basic_projects.mini_market.exception.transaction;

// Thrown when a transaction contains invalid data or is incomplete.
public class InvalidTransactionException extends RuntimeException {
  public InvalidTransactionException(String message) {
    super(message);
  }
}
