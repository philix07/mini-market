package com.felix.basic_projects.mini_market.exception.stock_entry;

public class StockEntryNotFoundException extends RuntimeException {
  public StockEntryNotFoundException(String message) {
    super(message);
  }
}
