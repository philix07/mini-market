package com.felix.basic_projects.mini_market.model.dto.response;

import com.felix.basic_projects.mini_market.model.entity.ProductTransactionSummary;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class SalesReportResponseDTO {

  private LocalDateTime reportDate;
  private double totalRevenue;
  private int totalTransactions;
  private List<ProductTransactionSummary> productTransactionSummaries; // Can we use groupBy function?


}
