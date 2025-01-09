package com.felix.basic_projects.mini_market.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.felix.basic_projects.mini_market.model.enums.PaymentMethod;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

// Represents sales transactions.
@Entity
public class Transaction {

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

  public Transaction() {}

  public Transaction(Long id, LocalDateTime transactionDate, PaymentMethod paymentMethod, List<TransactionItem> transactionItems, Customer customer, double totalPrice) {
    this.id = id;
    this.transactionDate = transactionDate;
    this.paymentMethod = paymentMethod;
    this.transactionItems = transactionItems;
    this.customer = customer;
    this.totalPrice = totalPrice;
  }

  public Transaction(PaymentMethod paymentMethod, List<TransactionItem> transactionItems, Customer customer) {
    this.paymentMethod = paymentMethod;
    this.transactionItems = transactionItems;
    this.customer = customer;
    this.totalPrice = recalculateTotalPrice();
  }

  public double recalculateTotalPrice() {
    return transactionItems.stream()
      .mapToDouble(TransactionItem::calculateTotal)
      .sum();
  }

  // This ensures that even if the POST payload does not include transactionDate and totalPrice,
  // it will be initialized at the database level.
  @PrePersist
  protected void onCreate() {
    if (transactionDate == null) {
      this.transactionDate = LocalDateTime.now();
    }
    this.totalPrice = recalculateTotalPrice();
  }

  // This ensures that totalPrice is recalculated whenever it is updated.
  @PreUpdate
  protected void calculateTotalPrice() {
    this.totalPrice = recalculateTotalPrice();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public LocalDateTime getTransactionDate() {
    return transactionDate;
  }

  public void setTransactionDate(LocalDateTime transactionDate) {
    this.transactionDate = transactionDate;
  }

  public PaymentMethod getPaymentMethod() {
    return paymentMethod;
  }

  public void setPaymentMethod(PaymentMethod paymentMethod) {
    this.paymentMethod = paymentMethod;
  }

  public List<TransactionItem> getTransactionItems() {
    return transactionItems;
  }

  public double getTotalPrice() {
    return totalPrice;
  }

  public void setTransactionItems(List<TransactionItem> transactionItems) {
    this.transactionItems = transactionItems;
    // Ensure each item references this transaction
    for (TransactionItem item : transactionItems) {
      item.setTransaction(this);
    }

    this.totalPrice = recalculateTotalPrice();
  }

  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  @Override
  public String toString() {
    return "Transaction{" +
      "id=" + id +
      ", transactionDate=" + transactionDate +
      ", totalPrice=" + recalculateTotalPrice() +
      ", paymentMethod='" + paymentMethod + '\'' +
      ", transactionItems=" + transactionItems +
      ", customer=" + customer +
      '}';
  }
}
  
