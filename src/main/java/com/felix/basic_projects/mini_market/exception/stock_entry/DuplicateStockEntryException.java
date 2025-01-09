package com.felix.basic_projects.mini_market.exception.stock_entry;

// Thrown when a specific transaction is not found.
public class DuplicateStockEntryException extends RuntimeException {
  public DuplicateStockEntryException(String message) {
    super(message);
  }
}
