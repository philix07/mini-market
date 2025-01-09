package com.felix.basic_projects.mini_market.exception.user;

// Thrown when attempting to create a user with an existing username or email.
public class UserAlreadyExistException extends RuntimeException {
  public UserAlreadyExistException(String message) {
    super(message);
  }
}
