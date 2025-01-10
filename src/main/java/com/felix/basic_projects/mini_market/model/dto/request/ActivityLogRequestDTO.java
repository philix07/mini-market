package com.felix.basic_projects.mini_market.model.dto.request;

import com.felix.basic_projects.mini_market.model.entity.User;
import com.felix.basic_projects.mini_market.model.entity.enums.ActivityLogResource;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
public class ActivityLogRequestDTO {

  @NotNull(message = "User ID must be filled")
  private Long userId; // The user who performed the action

  @NotEmpty(message = "action cannot be empty or null")
  private String action; // Description of the action

  @NotNull(message = "resource affected cannot be empty or null")
  @Enumerated(EnumType.STRING)
  private String resource; // Affected resource (e.g., Product, Transaction)

  private String detailsBefore;
  private String detailsAfter;

}

