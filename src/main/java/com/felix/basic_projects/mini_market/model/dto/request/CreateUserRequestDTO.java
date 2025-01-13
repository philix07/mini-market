package com.felix.basic_projects.mini_market.model.dto.request;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequestDTO {

  @Email(message = "Invalid email format provided")
  @NotEmpty(message = "User email cannot be empty or null")
  private String email;

  @NotEmpty(message = "Username cannot be empty or null")
  @Size(min = 4, message = "Username should have at least 4 character")
  private String username;

  @NotEmpty(message = "Password cannot be empty or null")
  @Size(min = 8, message = "Password should have at least 8 character")
  private String password;

  @NotEmpty(message = "User role cannot be empty or null")
  private String role; // e.g., "Admin", "Cashier"

}
