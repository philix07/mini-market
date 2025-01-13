package com.felix.basic_projects.mini_market.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.felix.basic_projects.mini_market.model.entity.enums.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "app_user")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id; // Primary Key

  @Email
  @NotEmpty(message = "User email cannot be empty or null")
  private String email;

  @NotEmpty(message = "Username cannot be empty or null")
  @Size(min = 4, message = "Username should have at least 4 character")
  private String username; // Unique username

  @NotEmpty(message = "Password cannot be empty or null")
  @Size(min = 8, message = "Password should have at least 8 character")
  private String password; // Encrypted password

  @Enumerated(EnumType.STRING)
  @NotNull(message = "User role cannot be null")
  private UserRole role; // e.g., "Admin", "Cashier"

  @NotNull(message = "User status cannot be null")
  private boolean isActive; // Status of the user

}
