package com.felix.basic_projects.mini_market.model.dto.request;

import com.felix.basic_projects.mini_market.model.entity.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UpdateStockEntryRequestDTO {

  @NotNull(message = "userId for the one who triggers the updates must provided")
  private Long userId;

  @NotNull(message = "Product id must be filled")
  private Long productId; // Foreign Key to Product

  @NotNull(message = "Quantity field value must be filled")
  @Positive(message = "Quantity value added must be positive")
  private int quantity; // Quantity added

  @NotNull(message = "totalPrice field value must be filled")
  @Positive(message = "totalPrice value must be positive")
  private double totalPrice; // Total price cost for single product

}
