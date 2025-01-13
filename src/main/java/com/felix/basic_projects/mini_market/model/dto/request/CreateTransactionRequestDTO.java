package com.felix.basic_projects.mini_market.model.dto.request;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.felix.basic_projects.mini_market.model.entity.Customer;
import com.felix.basic_projects.mini_market.model.entity.TransactionItem;
import com.felix.basic_projects.mini_market.model.entity.enums.PaymentMethod;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTransactionRequestDTO {

  @NotNull(message = "paymentMethod is not provided")
  private String paymentMethod;

  @NotNull(message = "There should be at least 1 transaction item for each transaction")
  private List<CreateTransactionItemRequestDTO> transactionItems;

  @NotNull(message = "customerId must be filled")
  private Long customerId;

  // force "totalPrice" to be serialized by adding the @JsonProperty annotation,
  // so we don't need to manually include the totalPrice in JSON payload
  @JsonProperty
  private double totalPrice; // Total price of all the transaction

}
  
