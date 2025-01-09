package com.felix.basic_projects.mini_market.exception.general;

// Thrown when trying to add a product that already exists.
public class DatabaseConnectionException extends RuntimeException {
  public DatabaseConnectionException(String message) {
    super(message);
  }
}
