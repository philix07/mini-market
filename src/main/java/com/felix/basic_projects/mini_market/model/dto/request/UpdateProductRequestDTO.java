package com.felix.basic_projects.mini_market.model.dto.request;

import com.felix.basic_projects.mini_market.model.entity.enums.ProductCategory;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;


@Getter
public class UpdateProductRequestDTO {

  @NotNull(message = "userId for the one who triggers the updates must provided")
  private Long userId;

  @NotEmpty(message = "Product cannot be empty or null")
  @Size(min = 4, message = "Product name should have at least 4 character")
  private String name;

  @NotNull(message = "Product category cannot be null")
  private String category;

  @NotEmpty(message = "Product barcode cannot be empty or null")
  private String barcode;

  @Positive(message = "Product price should be greater than 0")
  @NotNull(message = "Product price cannot be empty or null")
  private double price;

}
