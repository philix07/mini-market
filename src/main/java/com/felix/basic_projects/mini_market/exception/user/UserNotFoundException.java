package com.felix.basic_projects.mini_market.exception.user;

// Thrown when a user with a specific ID or username is not found.
public class UserNotFoundException extends RuntimeException {
  public UserNotFoundException(String message) {
    super(message);
  }
}
