package com.felix.basic_projects.mini_market.exception.transaction_item;

// Thrown when the transaction item details are invalid (e.g., negative quantity or price).
public class InvalidTransactionItemException extends RuntimeException {
  public InvalidTransactionItemException(String message) {
    super(message);
  }
}
