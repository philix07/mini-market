package com.felix.basic_projects.mini_market.service;

import com.felix.basic_projects.mini_market.exception.customer.CustomerNotFoundException;
import com.felix.basic_projects.mini_market.exception.customer.InvalidCustomerDetailsException;
import com.felix.basic_projects.mini_market.exception.product.InvalidStockQuantityException;
import com.felix.basic_projects.mini_market.exception.product.ProductNotFoundException;
import com.felix.basic_projects.mini_market.exception.transaction.DuplicateTransactionException;
import com.felix.basic_projects.mini_market.exception.transaction.TransactionNotFoundException;
import com.felix.basic_projects.mini_market.model.Customer;
import com.felix.basic_projects.mini_market.model.Product;
import com.felix.basic_projects.mini_market.model.Transaction;
import com.felix.basic_projects.mini_market.model.TransactionItem;
import com.felix.basic_projects.mini_market.repository.CustomerRepository;
import com.felix.basic_projects.mini_market.repository.ProductRepository;
import com.felix.basic_projects.mini_market.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TransactionService {

  @Autowired
  private TransactionRepository transactionRepository;

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private CustomerRepository customerRepository;

  public List<Transaction> retrieveAllTransaction() {
    List<Transaction> transactions = transactionRepository.findAll();
    if(transactions.isEmpty()) {
      throw new TransactionNotFoundException("There is no transaction in this application");
    }
    return transactions;
  }

  public Transaction findTransactionById(Long id) {
    return transactionRepository.findById(id)
      .orElseThrow(() -> new TransactionNotFoundException("No transaction with id : " + id));
  }

  // Why TransactionItemRepository is not required?
  // -------- Cascading Saves --------
  // Transaction class uses cascade = CascadeType.ALL for the transactionItems
  // relationship. This means when we save a Transaction, JPA will automatically
  // persist all associated TransactionItem entities.

  // -------- Orphan Removal --------
  // The orphanRemoval = true setting ensures that any TransactionItem removed
  // from the transactionItems list will also be deleted from the database.

  // -------- Maintaining Relationships --------
  // The mappedBy attribute in the @OneToMany annotation specifies that the transaction
  // field in TransactionItem owns the relationship. As long as you set the transaction
  // field in each TransactionItem before saving the Transaction, JPA will handle saving
  // the TransactionItem entities for you.

  // The @Transactional annotation ensures the operation is atomic. If anything fails
  // (e.g., a validation error), the entire transaction will be rolled back.
  @Transactional
  public Transaction saveTransaction(Transaction transaction) {
    if(transaction.getId() != null && transactionRepository.existsById(transaction.getId())) {
      throw new DuplicateTransactionException("Transaction with id:" + transaction.getId() + "already exists");
    }

    if (transaction.getCustomer() == null || transaction.getCustomer().getId() == null) {
      throw new InvalidCustomerDetailsException("Customer ID must not be null");
    }

    Transaction mappedTransaction = mapTransactionDetail(transaction, TransactionOperationType.SAVE_TRANSACTION);

    return transactionRepository.save(mappedTransaction);
  }

  @Transactional
  public Transaction deleteTransactionById(Long id) {
    Transaction transaction = mapTransactionDetail(
      transactionRepository
        .findById(id)
        .orElseThrow(() -> new TransactionNotFoundException("No transaction with id : " + id)),
      TransactionOperationType.DELETE_TRANSACTION
    );

    transactionRepository.delete(transaction);
    return transaction;
  }

  @Transactional
  public Transaction updateTransactionById(Long id, Transaction transaction) {
    Transaction oldTransaction = transactionRepository.findById(id)
      .orElseThrow(() -> new TransactionNotFoundException("No transaction with id : " + id));

    oldTransaction.setPaymentMethod(transaction.getPaymentMethod());

    // map List<TransactionItem> into Map for easier operation
    Map<Long, TransactionItem> newTransactionItems = transaction.getTransactionItems().stream()
      .collect(Collectors.toMap(
        item -> item.getProduct().getId(),
        item -> item
      ));

    // list manipulation for already existing Product in previous TransactionItem
    // creating an array reference so we don't mess with the original list
    List<TransactionItem> oldTransactionItem = new ArrayList<>(oldTransaction.getTransactionItems());
    for (TransactionItem transactionItem : oldTransactionItem) {
      if(newTransactionItems.containsKey(transactionItem.getProduct().getId())) {
        TransactionItem updatedTransactionItem = newTransactionItems.get(transactionItem.getProduct().getId());

        // updating the Product's stockQuantity for already existing Product in the previous Transaction
        Product updatedProductStockQuantity = productRepository.findById(transactionItem.getProduct().getId())
          .map(
            product -> {
              product.setStockQuantity(product.getStockQuantity() + (updatedTransactionItem.getQuantity() - transactionItem.getQuantity()));
              if(product.getStockQuantity() < 0) {
                throw new InvalidStockQuantityException(
                  "Not enough stock for product : '" + product.getName() +"', stock after transaction : " + product.getStockQuantity()
                );
              }

              return productRepository.saveAndFlush(product);
            }
          )
          .orElseThrow(
            () -> new ProductNotFoundException("There is no product with id : " + transactionItem.getProduct().getId())
          );

        // Since both "oldTransactionItem" and "oldTransaction.getTransactionItems()" referenced
        // the same TransactionItem object, when there is change of fields (modification/updates),
        // it affects both of them. But this only works for updating feature, not with deleting or adding.
        // That's why when i updated the "oldTransactionItem" field, the "oldTransaction.getTransactionItems()"
        // is also affected without me to directly accessing the "List<TransactionItem>" from the "oldTransaction"

        // But turns out Hibernate can handle this without creating a new list.
        // In my case, oldTransaction.getTransactionItems() is likely an instance of PersistentBag,
        // which is a type of collection used by Hibernate for managing one-to-many relationships.
        // The behavior of PersistentBag when modified during iteration does not strictly follow the same
        // rules as standard Java collections like ArrayList.
        transactionItem.setPrice(updatedTransactionItem.getPrice());
        transactionItem.setQuantity(updatedTransactionItem.getQuantity());
      } else {
        // Remove item if it's not on the list
        oldTransaction.getTransactionItems().remove(transactionItem);
      }
    }

    List<TransactionItem> updatedTransactionItems = oldTransaction.getTransactionItems();
    for (TransactionItem newItem : newTransactionItems.values()) {
      boolean alreadyExists = oldTransaction.getTransactionItems().stream()
        .anyMatch(
          item -> newItem.getProduct().getId().equals(item.getProduct().getId())
        );

      if(!alreadyExists) {

        // updating the Product's stockQuantity for non-existing Product in the previous Transaction
        Product updatedProductStockQuantity = productRepository.findById(newItem.getProduct().getId())
          .map(
            product -> {
              product.setStockQuantity(product.getStockQuantity() - newItem.getQuantity());
              if(product.getStockQuantity() < 0) {
                throw new InvalidStockQuantityException(
                  "Not enough stock for product : '" + product.getName() +"', stock after transaction : " + product.getStockQuantity()
                );
              }

              return productRepository.saveAndFlush(product);
            }
          )
          .orElseThrow(
            () -> new ProductNotFoundException("There is no product with id : " + newItem.getProduct().getId())
          );

        updatedTransactionItems.add(newItem);
      }
    }

    oldTransaction.setTransactionItems(updatedTransactionItems);
    return transactionRepository.save(oldTransaction);
  }

  @Transactional
  private Transaction mapTransactionDetail(Transaction transaction, TransactionOperationType operationType) {
    Customer customer = customerRepository.findById(transaction.getCustomer().getId())
      .orElseThrow(
        () -> new CustomerNotFoundException("Customer with ID : " + transaction.getCustomer().getId() + " not found")
      );
    transaction.setCustomer(new Customer(
      customer.getId(),
      customer.getEmail(),
      customer.getName(),
      customer.getContactNumber()
    ));

    if (transaction.getTransactionItems() != null) {
      for (TransactionItem transactionItem : transaction.getTransactionItems()) {
        transactionItem.setTransaction(transaction); // Set parent transaction
        Product mappedProduct = productRepository.findById(transactionItem.getProduct().getId())
          .map(
            // synchronize Product's stockQuantity data in the database
            product -> {

              if(operationType == TransactionOperationType.SAVE_TRANSACTION) {
                product.setStockQuantity(product.getStockQuantity() - transactionItem.getQuantity());
              } else if (operationType == TransactionOperationType.DELETE_TRANSACTION) {
                product.setStockQuantity(product.getStockQuantity() + transactionItem.getQuantity());
              }

              if(product.getStockQuantity() < 0) {
                throw new InvalidStockQuantityException(
                  "Not enough stock for product : '" + product.getName() +"', stock after transaction : " + product.getStockQuantity()
                );
              }

              return productRepository.saveAndFlush(product);
            }
          )
          .orElseThrow(
            () -> new ProductNotFoundException("Product with ID : " + transactionItem.getProduct().getId() + " not found")
          );

        transactionItem.setProduct(mappedProduct);
      }
    }
    return transaction;
  }

  private enum TransactionOperationType {
    SAVE_TRANSACTION, DELETE_TRANSACTION
  }
}


