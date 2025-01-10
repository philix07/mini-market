package com.felix.basic_projects.mini_market.model.dto.response;

import com.felix.basic_projects.mini_market.model.entity.User;
import com.felix.basic_projects.mini_market.model.entity.enums.ActivityLogResource;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActivityLogResponseDTO {
  private Long id;
  private Long userId;
  private LocalDateTime createdAt;
  private String action;
  private String resource;
  private String detailsAfter;
  private String detailsBefore;
}

