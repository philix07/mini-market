package com.felix.basic_projects.mini_market.mapper;

import com.felix.basic_projects.mini_market.model.dto.response.ActivityLogResponseDTO;
import com.felix.basic_projects.mini_market.model.dto.response.TransactionItemResponseDTO;
import com.felix.basic_projects.mini_market.model.dto.response.TransactionResponseDTO;
import com.felix.basic_projects.mini_market.model.entity.ActivityLog;
import com.felix.basic_projects.mini_market.model.entity.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionMapper {

  /*
    TransactionResponseDTO's field
      private Long id;
      private Long customerId;
      private Long userId;
      private String customerName;
      private LocalDateTime transactionDate;
      private PaymentMethod paymentMethod;
      private List<TransactionItemResponseDTO> transactionItems;
      private double totalPrice;
   */
  public TransactionResponseDTO mapEntityToResponseDTO(Transaction transaction) {
    TransactionItemMapper itemMapper = new TransactionItemMapper();

    List<TransactionItemResponseDTO> itemResponseDTOS = transaction.getTransactionItems().stream()
      .map(itemMapper::mapEntityToResponseDTO)
      .toList();

    return TransactionResponseDTO.builder()
      .id(transaction.getId())
      .customerId(transaction.getCustomer().getId())
      .customerName(transaction.getCustomer().getName())
      .userId(transaction.getUser().getId())
      .username(transaction.getUser().getUsername())
      .transactionDate(transaction.getTransactionDate())
      .paymentMethod(transaction.getPaymentMethod())
      .transactionItems(itemResponseDTOS)
      .totalPrice(transaction.getTotalPrice())
      .build();
  }

}
