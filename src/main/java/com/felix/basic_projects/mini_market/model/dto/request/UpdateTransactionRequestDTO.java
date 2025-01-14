package com.felix.basic_projects.mini_market.model.dto.request;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.felix.basic_projects.mini_market.model.entity.Customer;
import com.felix.basic_projects.mini_market.model.entity.TransactionItem;
import com.felix.basic_projects.mini_market.model.entity.enums.PaymentMethod;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class UpdateTransactionRequestDTO {

  @NotNull(message = "userId for the one who triggers the updates must provided")
  private Long userId;

  @NotNull(message = "Payment method field must be provided")
  private PaymentMethod paymentMethod;

  @NotNull(message = "Updated transaction item must be provided")
  private List<CreateTransactionItemRequestDTO> transactionItems;

}
  
