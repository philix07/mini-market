package com.felix.basic_projects.mini_market.service;

import com.felix.basic_projects.mini_market.exception.customer.CustomerNotFoundException;
import com.felix.basic_projects.mini_market.exception.product.InvalidStockQuantityException;
import com.felix.basic_projects.mini_market.exception.product.ProductNotFoundException;
import com.felix.basic_projects.mini_market.exception.transaction.TransactionNotFoundException;
import com.felix.basic_projects.mini_market.exception.user.UserNotFoundException;
import com.felix.basic_projects.mini_market.mapper.TransactionItemMapper;
import com.felix.basic_projects.mini_market.mapper.TransactionMapper;
import com.felix.basic_projects.mini_market.model.dto.request.CreateTransactionRequestDTO;
import com.felix.basic_projects.mini_market.model.dto.request.UpdateTransactionRequestDTO;
import com.felix.basic_projects.mini_market.model.dto.response.TransactionResponseDTO;
import com.felix.basic_projects.mini_market.model.entity.*;
import com.felix.basic_projects.mini_market.model.entity.enums.PaymentMethod;
import com.felix.basic_projects.mini_market.repository.CustomerRepository;
import com.felix.basic_projects.mini_market.repository.ProductRepository;
import com.felix.basic_projects.mini_market.repository.TransactionRepository;
import com.felix.basic_projects.mini_market.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private TransactionMapper transactionMapper;

  @Autowired
  private TransactionItemMapper transactionItemMapper;

  public List<TransactionResponseDTO> retrieveAllTransaction() {
    List<Transaction> transactions = transactionRepository.findAll();

    if(transactions.isEmpty()) {
      throw new TransactionNotFoundException("There is no transaction in this application");
    }

    return transactions.stream().map(transactionMapper::mapEntityToResponseDTO).toList();
  }

  public TransactionResponseDTO findTransactionById(Long id) {
    return transactionRepository.findById(id)
      .map(transactionMapper::mapEntityToResponseDTO)
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
  public TransactionResponseDTO saveTransaction(CreateTransactionRequestDTO transactionRequest) {
    Long customerId = transactionRequest.getCustomerId();
    Long userId = transactionRequest.getUserId();

    // fetch customer details
    Customer customer = customerRepository.findById(customerId)
      .orElseThrow(() -> new CustomerNotFoundException("There is no customer with id : " + customerId));

    // fetch user details
    User user = userRepository.findById(userId)
      .orElseThrow(() -> new UserNotFoundException("There is no user with id : " + userId));

    // map from List<Request> into entity object List<TransactionItem>
    List<TransactionItem> transactionItems = transactionRequest.getTransactionItems()
      .stream()
      .map(
        requestDTO -> {
          Product mappedProduct = productRepository.findById(requestDTO.getProductId())
            .map(
              // synchronize Product's stockQuantity data in the database
              product -> {
                product.setStockQuantity(product.getStockQuantity() - requestDTO.getQuantity());

                if(product.getStockQuantity() < 0) {
                  throw new InvalidStockQuantityException(
                    "Not enough stock for product : '" + product.getName() +"', stock after transaction : " + product.getStockQuantity()
                  );
                }

                return productRepository.saveAndFlush(product);
              }
            )
            .orElseThrow(
              () -> new ProductNotFoundException("Product with ID : " + requestDTO.getProductId() + " not found")
            );

          TransactionItem item = new TransactionItem();
          item.setProduct(mappedProduct);
          item.setPrice(mappedProduct.getPrice());
          item.setQuantity(requestDTO.getQuantity());

          return item;
        }
      )
      .toList();

    Transaction transaction = new Transaction();
      transaction.setTransactionDate(LocalDateTime.now());
      transaction.setUser(user);
      transaction.setCustomer(customer);
      transaction.setPaymentMethod(transactionRequest.getPaymentMethod());
      transaction.setTransactionItems(transactionItems);

    transactionRepository.save(transaction);
    return transactionMapper.mapEntityToResponseDTO(transaction);
  }



  @Transactional
  public TransactionResponseDTO deleteTransactionById(Long id) {
    Transaction transaction = transactionRepository.findById(id)
        .orElseThrow(() -> new TransactionNotFoundException("No transaction with id : " + id));

    // Synchronize the quantity when the transaction item is deleted
    for (TransactionItem item : transaction.getTransactionItems()) {
      Product product = productRepository.findById(item.getProduct().getId())
        .orElseThrow(() -> new ProductNotFoundException("There is no product with id : " + item.getProduct().getId()));

      product.setStockQuantity(product.getStockQuantity() + item.getQuantity());
      productRepository.save(product);
    }

    transactionRepository.delete(transaction);
    return transactionMapper.mapEntityToResponseDTO(transaction);
  }



  @Transactional
  public TransactionResponseDTO updateTransactionById(Long id, UpdateTransactionRequestDTO transaction) {
    Transaction oldTransaction = transactionRepository.findById(id)
      .orElseThrow(() -> new TransactionNotFoundException("No transaction with id : " + id));

    oldTransaction.setPaymentMethod(transaction.getPaymentMethod());

    // map List<CreateTransactionItemRequestDTO> into List<TransactionItem> for easier operation
    List<TransactionItem> newTransactionItem = transaction.getTransactionItems()
      .stream()
      .map(
        newItem -> {
          Product product = productRepository.findById(newItem.getProductId())
            .orElseThrow(
              () -> new ProductNotFoundException("Product with ID : " + newItem.getProductId() + " not found")
            );

          TransactionItem item = new TransactionItem();
          item.setProduct(product);
          item.setPrice(product.getPrice());
          item.setQuantity(newItem.getQuantity());

          return item;
        }
      )
      .toList();


    // map List<TransactionItem> into Map for easier operation
    Map<Long, TransactionItem> newTransactionItems = newTransactionItem.stream()
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
              product.setStockQuantity(product.getStockQuantity() + (transactionItem.getQuantity() - updatedTransactionItem.getQuantity()));
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
        // That's why when I updated the "oldTransactionItem" field, the "oldTransaction.getTransactionItems()"
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
    transactionRepository.save(oldTransaction);

    return transactionMapper.mapEntityToResponseDTO(oldTransaction);
  }

}


