package com.felix.basic_projects.mini_market.mapper;

import com.felix.basic_projects.mini_market.model.dto.response.TransactionItemResponseDTO;
import com.felix.basic_projects.mini_market.model.entity.TransactionItem;
import org.springframework.stereotype.Service;

@Service
public class TransactionItemMapper {

  /*
    TransactionItemResponseDTO's field
      private Long id;
      private Long productId;
      private String productName;
      private int quantity;
      private double price;
      private double total;
   */
  public TransactionItemResponseDTO mapEntityToResponseDTO(TransactionItem item) {
    return TransactionItemResponseDTO.builder()
      .id(item.getId())
      .productId(item.getProduct().getId())
      .productName(item.getProduct().getName())
      .quantity(item.getQuantity())
      .price(item.getPrice())
      .total(item.getTotal())
      .build();
  }

}
