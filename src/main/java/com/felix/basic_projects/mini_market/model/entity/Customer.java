package com.felix.basic_projects.mini_market.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Customer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id; // Primary Key

  @Email
  @NotEmpty(message = "Customer email cannot be empty or null")
  private String email; // Email address

  @NotEmpty(message = "Customer name cannot be empty or null")
  @Size(min = 4, message = "Customer name should have at least 4 character")
  private String name; // Customer name

  @NotEmpty(message = "Contact number cannot be empty or null")
  @Size(min = 10, message = "Contact number should have at least 10 character")
  private String contactNumber; // Contact details

}
