package com.felix.basic_projects.mini_market.model.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreateActivityLogRequestDTO {

  @NotNull(message = "User ID must be filled")
  private Long userId; // The user who performed the action

  @NotEmpty(message = "action cannot be empty or null")
  private String action; // Description of the action

  @NotNull(message = "resource affected cannot be empty or null")
  private String resource; // Affected resource (e.g., Product, Transaction)

  private String detailsBefore;
  private String detailsAfter;

}

