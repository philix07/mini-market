package com.felix.basic_projects.mini_market.exception.activity_log;

// Thrown when a product with a given ID or name is not found in the database.
public class InvalidActivityLogException extends RuntimeException {
  public InvalidActivityLogException(String message) {
    super(message);
  }
}
