package com.felix.basic_projects.mini_market.mapper;

import com.felix.basic_projects.mini_market.model.dto.response.StockEntryResponseDTO;
import com.felix.basic_projects.mini_market.model.entity.StockEntry;
import org.springframework.stereotype.Service;

@Service
public class StockEntryMapper {

  /*
    StockEntryResponseDTO's field
      private Long id;
      private LocalDateTime createdAt;
      private String productId;
      private int quantity;
      private double totalPrice;
   */
  public StockEntryResponseDTO mapEntityToResponseDTO(StockEntry stockEntry) {
    return StockEntryResponseDTO.builder()
      .id(stockEntry.getId())
      .createdAt(stockEntry.getCreatedAt())
      .productId(stockEntry.getProduct().getId())
      .userId(stockEntry.getUser().getId())
      .quantity(stockEntry.getQuantity())
      .totalPrice(stockEntry.getTotalPrice())
      .build();
  }

}
