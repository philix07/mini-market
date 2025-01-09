package com.felix.basic_projects.mini_market.exception.transaction_item;

// Thrown when a specific transaction item is not found.
public class TransactionItemNotFound extends RuntimeException {
  public TransactionItemNotFound(String message) {
    super(message);
  }
}
