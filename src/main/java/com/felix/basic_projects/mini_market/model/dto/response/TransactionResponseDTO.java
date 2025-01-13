package com.felix.basic_projects.mini_market.model.dto.response;


import com.felix.basic_projects.mini_market.model.entity.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionResponseDTO {
  private Long id;
  private Long customerId;
  private String customerName;
  private Long userId;
  private String username;
  private LocalDateTime transactionDate;
  private PaymentMethod paymentMethod;
  private List<TransactionItemResponseDTO> transactionItems;
  private double totalPrice;
}
  
