package com.felix.basic_projects.mini_market.model.dto.request;

import com.felix.basic_projects.mini_market.model.entity.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
public class CreateStockEntryRequestDTO {

  @NotNull(message = "Product id field must be provided")
  private Long productId;

  @NotNull(message = "User id field must be provided")
  private Long userId;

  @NotNull(message = "Quantity field value must be filled")
  @Positive(message = "Quantity value added must be positive")
  private int quantity;

  @NotNull(message = "totalPrice field value must be filled")
  @Positive(message = "totalPrice value must be positive")
  private double totalPrice;

}
