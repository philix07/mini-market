package com.felix.basic_projects.mini_market.model.dto.request;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.felix.basic_projects.mini_market.model.entity.Customer;
import com.felix.basic_projects.mini_market.model.entity.TransactionItem;
import com.felix.basic_projects.mini_market.model.entity.enums.PaymentMethod;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

// Represents sales transactions.
public class UpdateTransactionRequestDTO {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id; // Primary Key

  private LocalDateTime transactionDate; // Date and time of the transaction

  @Enumerated(EnumType.STRING)
  private PaymentMethod paymentMethod; // e.g., "Cash", "Credit Card", "E-wallet"

  // @JsonManagedReference is used on the transactionItems field of Transaction,
  // indicating that TransactionItem will manage the relationship.
  @JsonManagedReference // Prevent infinite recursion when serializing Transaction
  // cascade = CascadeType.ALL: Automatically persist, merge, or remove
  // TransactionItems when the Transaction is persisted, updated, or deleted.
  // orphanRemoval = true: Removes TransactionItems that are no longer associated with the Transaction.
  @OneToMany(mappedBy = "transaction", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<TransactionItem> transactionItems; // List of items in the transaction

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "customer_id")
  private Customer customer; // Optional: Customer ID if linked

  // force "totalPrice" to be serialized by adding the @JsonProperty annotation,
  // so we don't need to manually include the totalPrice in JSON payload
  @JsonProperty
  private double totalPrice; // Total price of all the transaction

}
  
