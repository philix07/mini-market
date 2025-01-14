package com.felix.basic_projects.mini_market.model.entity;

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
@Entity
public class ActivityLog {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull(message = "creation date of ActivityLog must be filled")
  private LocalDateTime createdAt;

  @ManyToOne(fetch = FetchType.EAGER)
  @NotNull(message = "Product id must be filled")
  private User user; // The user who performed the action

  @NotEmpty(message = "action cannot be empty or null")
  private String action; // Description of the action

  @NotNull(message = "ActivityLog resource must be provided")
  @Enumerated(EnumType.STRING)
  private ActivityLogResource resource; // Affected resource (e.g., Product, Transaction)

  @Column(nullable = true, length = 1000)
  private String detailsBefore; // Details before the operation (e.g., JSON of the resource)

  @Column(nullable = true, length = 1000)
  private String detailsAfter; // Details after the operation (e.g., JSON of the updated resource)


}

