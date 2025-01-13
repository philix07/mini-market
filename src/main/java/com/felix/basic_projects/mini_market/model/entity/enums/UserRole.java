package com.felix.basic_projects.mini_market.model.entity.enums;

public enum UserRole {

  OWNER,          // Highest-level user with full control over the application
  ADMIN,          // Manager responsible for operations and reports
  CASHIER;        // Handles transactions and payments

  public static UserRole fromString(String role) {
    if (role == null || role.trim().isEmpty()) {
      throw new IllegalArgumentException("Role cannot be null or empty.");
    }
    try {
      return UserRole.valueOf(role.trim().toUpperCase());
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Invalid role: " + role + ". Allowed values are: " + getAllowedRoles());
    }
  }

  private static String getAllowedRoles() {
    StringBuilder allowedRoles = new StringBuilder();
    for (UserRole role : UserRole.values()) {
      if (!allowedRoles.isEmpty()) {
        allowedRoles.append(", ");
      }
      allowedRoles.append(role.name());
    }
    return allowedRoles.toString();
  }

}
