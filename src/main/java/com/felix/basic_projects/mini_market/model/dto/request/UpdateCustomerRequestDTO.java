package com.felix.basic_projects.mini_market.model.dto.request;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
public class UpdateCustomerRequestDTO {

  @NotNull(message = "userId for the one who triggers the updates must provided")
  private Long updatedById;

  @Email
  @NotEmpty(message = "Customer email cannot be empty or null")
  private String email;

  @NotEmpty(message = "Customer name cannot be empty or null")
  @Size(min = 4, message = "Customer name should have at least 4 character")
  private String name;

  @NotEmpty(message = "Contact number cannot be empty or null")
  @Size(min = 10, message = "Contact number should have at least 10 character")
  private String contactNumber;

}

