package com.felix.basic_projects.mini_market.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.felix.basic_projects.mini_market.model.entity.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

// Represents sales transactions.
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Transaction {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Setter
  private Long id; // Primary Key

  @Setter
  private LocalDateTime transactionDate; // Date and time of the transaction

  @Setter
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

  @Setter
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "customer_id")
  private Customer customer; // Link to Customer ID

  @Setter
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id")
  private User user; // Link to USER ID

  @JsonProperty
  private double totalPrice; // Total price of all the transaction

  public double recalculateTotalPrice() {
    return transactionItems.stream()
      .mapToDouble(TransactionItem::calculateTotal)
      .sum();
  }

  public void setTransactionItems(List<TransactionItem> transactionItems) {
    this.transactionItems = transactionItems;
    // Ensure each item references this transaction
    for (TransactionItem item : transactionItems) {
      item.setTransaction(this);
    }

    this.totalPrice = recalculateTotalPrice();
  }

}
  
