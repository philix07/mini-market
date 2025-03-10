package com.felix.basic_projects.mini_market.model.dto.response;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.felix.basic_projects.mini_market.model.entity.Product;
import com.felix.basic_projects.mini_market.model.entity.Transaction;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionItemResponseDTO {
  private Long id;
  private Long productId;
  private String productName;
  private int quantity;
  private double price;
  private double total;
}
