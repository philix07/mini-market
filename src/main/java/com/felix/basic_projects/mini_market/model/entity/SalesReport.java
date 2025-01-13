package com.felix.basic_projects.mini_market.model.entity;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalesReport {

  private LocalDateTime reportDate;
  private double totalRevenue;
  private int totalTransactions;
  private List<ProductTransactionSummary> productTransactionSummaries; // Can we use groupBy function?


}
