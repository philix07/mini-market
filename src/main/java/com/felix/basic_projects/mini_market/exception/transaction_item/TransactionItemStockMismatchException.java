package com.felix.basic_projects.mini_market.exception.transaction_item;

// Thrown when the stock level does not match the transaction request.
public class TransactionItemStockMismatchException extends RuntimeException {
  public TransactionItemStockMismatchException(String message) {
    super(message);
  }
}
