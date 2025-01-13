package com.felix.basic_projects.mini_market.model.dto.request;

import com.felix.basic_projects.mini_market.model.entity.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateStockEntryRequestDTO {

  @NotNull(message = "Product id must be filled")
  private Long productId;

  @NotNull(message = "Quantity field value must be filled")
  @Positive(message = "Quantity value added must be positive")
  private int quantity;

  @NotNull(message = "totalPrice field value must be filled")
  @Positive(message = "totalPrice value must be positive")
  private double totalPrice;

}
