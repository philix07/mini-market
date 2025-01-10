package com.felix.basic_projects.mini_market.model.entity;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductTransactionSummary {

  private Product product;
  private int quantitySold;
  private double totalPrice;

}
