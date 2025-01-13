package com.felix.basic_projects.mini_market.model.dto.request;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.felix.basic_projects.mini_market.model.entity.Product;
import com.felix.basic_projects.mini_market.model.entity.Transaction;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTransactionItemRequestDTO {

  @NotNull(message = "Product id must be filled")
  private Long productId;

  @Min(value = 1, message = "Quantity must be at least 1")
  private int quantity;

  @PositiveOrZero(message = "Price should be >= 0")
  private double price;

}
