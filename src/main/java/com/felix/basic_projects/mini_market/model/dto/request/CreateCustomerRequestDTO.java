package com.felix.basic_projects.mini_market.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateCustomerRequestDTO {

  @Email(message = "Invalid email format provided")
  @NotEmpty(message = "Customer email cannot be empty or null")
  private String email; // Email address

  @NotEmpty(message = "Customer name cannot be empty or null")
  @Size(min = 4, message = "Customer name should have at least 4 character")
  private String name; // Customer name

  @NotEmpty(message = "Contact number cannot be empty or null")
  @Size(min = 10, message = "Contact number should have at least 10 character")
  private String contactNumber; // Contact details

}
