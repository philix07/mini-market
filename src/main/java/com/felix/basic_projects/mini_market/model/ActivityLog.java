package com.felix.basic_projects.mini_market.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
public class ActivityLog {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private LocalDateTime createdAt;

  @ManyToOne(fetch = FetchType.EAGER)
  @NotNull(message = "Product id must be filled")
  private User user; // The user who performed the action

  @NotEmpty(message = "action cannot be empty or null")
  private String action; // Description of the action

  @NotNull(message = "resource cannot be empty or null")
  @Enumerated(EnumType.STRING)
  private Resource resource; // Affected resource (e.g., Product, Transaction)

  @Column(length = 1000)
  private String details; // Additional details (e.g., JSON of the resource before/after update)

  private enum Resource {
    TRANSACTION, PRODUCT, CUSTOMER, STOCK_ENTRY, USER, SALES_REPORT;
  }

  @PrePersist
  void onCreate() {
    this.createdAt = LocalDateTime.now();
  }


}
