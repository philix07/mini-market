package com.felix.basic_projects.mini_market.exception.user;

// Thrown when user login fails due to invalid credentials.
public class UserAuthenticationException extends RuntimeException {
  public UserAuthenticationException(String message) {
    super(message);
  }
}
