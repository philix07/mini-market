package com.felix.basic_projects.mini_market.exception.user;

// Thrown when a user attempts to access a restricted resource.
public class UanthorizedAccessException extends RuntimeException {
  public UanthorizedAccessException(String message) {
    super(message);
  }
}
