package com.felix.basic_projects.mini_market.model.dto.response;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserResponseDTO {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id; // Primary Key

  @Email
  @NotEmpty(message = "User email cannot be empty or null")
  private String email;

  @NotEmpty(message = "Username cannot be empty or null")
  @Size(min = 4, message = "Username should have at least 4 character")
  private String username; // Unique username

  //TODO: When we fetch the users data, this field is supposed to be hidden
  @NotEmpty(message = "Password cannot be empty or null")
  @Size(min = 8, message = "Password should have at least 8 character")
  private String password; // Encrypted password

  @NotEmpty(message = "User role cannot be empty or null")
  private String role; // e.g., "Admin", "Cashier"

  @NotNull(message = "User status cannot be null")
  private boolean isActive; // Status of the user

  public UserResponseDTO() {}

  public UserResponseDTO(String email, String username, String password, String role, boolean isActive) {
    this.email = email;
    this.username = username;
    this.password = password;
    this.role = role;
    this.isActive = isActive;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public boolean isActive() {
    return isActive;
  }

  public void setActive(boolean active) {
    this.isActive = active;
  }

  @Override
  public String toString() {
    return "User{" +
      "id=" + id +
      ", email='" + email + '\'' +
      ", username='" + username + '\'' +
      ", password='" + password + '\'' +
      ", role='" + role + '\'' +
      ", isActive=" + isActive +
      '}';
  }
}
