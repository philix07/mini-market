package com.felix.basic_projects.mini_market.service;

import com.felix.basic_projects.mini_market.exception.product.InvalidStockQuantityException;
import com.felix.basic_projects.mini_market.exception.product.ProductNotFoundException;
import com.felix.basic_projects.mini_market.exception.stock_entry.StockEntryNotFoundException;
import com.felix.basic_projects.mini_market.exception.user.UserNotFoundException;
import com.felix.basic_projects.mini_market.mapper.StockEntryMapper;
import com.felix.basic_projects.mini_market.model.dto.request.CreateStockEntryRequestDTO;
import com.felix.basic_projects.mini_market.model.dto.request.UpdateStockEntryRequestDTO;
import com.felix.basic_projects.mini_market.model.dto.response.StockEntryResponseDTO;
import com.felix.basic_projects.mini_market.model.entity.ActivityLog;
import com.felix.basic_projects.mini_market.model.entity.Product;
import com.felix.basic_projects.mini_market.model.entity.StockEntry;
import com.felix.basic_projects.mini_market.model.entity.User;
import com.felix.basic_projects.mini_market.model.entity.enums.ActivityLogResource;
import com.felix.basic_projects.mini_market.repository.ActivityLogRepository;
import com.felix.basic_projects.mini_market.repository.ProductRepository;
import com.felix.basic_projects.mini_market.repository.StockEntryRepository;
import com.felix.basic_projects.mini_market.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StockEntryService {

  @Autowired
  private StockEntryRepository stockEntryRepository;
  @Autowired
  private ProductRepository productRepository;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private ActivityLogRepository activityLogRepository;

  @Autowired
  private StockEntryMapper stockEntryMapper;

  public List<StockEntryResponseDTO> retrieveAllStockEntry() {
    List<StockEntry> stockEntries = stockEntryRepository.findAll();

    if(stockEntries.isEmpty()) {
      throw new StockEntryNotFoundException("There is no stock entry in this application");
    }

    return stockEntries.stream().map(stockEntryMapper::mapEntityToResponseDTO).toList();
  }

  public StockEntryResponseDTO findStockEntryById(Long id) {
    return stockEntryRepository.findById(id)
      .map(stockEntryMapper::mapEntityToResponseDTO)
      .orElseThrow(() -> new StockEntryNotFoundException("There is no stock entry with id : " + id));
  }

  @Transactional
  public StockEntryResponseDTO saveStockEntry(CreateStockEntryRequestDTO stockEntryRequest) {

    // synchronize Product's stockQuantity data in the database
    Product updatedProduct = productRepository.findById(stockEntryRequest.getProductId())
      .map(
        product -> {
          product.setStockQuantity(product.getStockQuantity() + stockEntryRequest.getQuantity());
          if(product.getStockQuantity() < 0) {
            throw new InvalidStockQuantityException("Product : '"+ product.getName()+ "' stock quantity becomes invalid : " + product.getStockQuantity());
          }

          // Using saveAndFlush ensures that all changes made during the transaction are visible and persisted
          // to the database immediately, preventing issues when attempting to reference the updated entity
          // in subsequent operations......
          // saveAndFlush: Saves the entity and immediately flushes changes to the database, ensuring that
          // subsequent operations reflect the most current data......
          // Without flushing immediately, the next operation (save or saveAndFlush) might still see
          // stale data from memory, causing exceptions or incorrect results......
          return productRepository.saveAndFlush(product);
        }
      )
      .orElseThrow(
        () -> new ProductNotFoundException("There is no product with id : " + stockEntryRequest.getProductId())
      );

    // fetch detail of user who performed the StockEntry operation
    User user = userRepository.findById(stockEntryRequest.getUserId())
      .orElseThrow(() -> new UserNotFoundException("There is no user with id : " + stockEntryRequest.getUserId()));

    StockEntry stockEntry = StockEntry.builder()
      .user(user)
      .createdAt(LocalDateTime.now())
      .product(updatedProduct)
      .quantity(stockEntryRequest.getQuantity())
      .totalPrice(stockEntryRequest.getTotalPrice())
      .build();
    stockEntryRepository.save(stockEntry);

    StockEntryResponseDTO stockEntryResponseDTO = stockEntryMapper.mapEntityToResponseDTO(stockEntry);

    ActivityLog activityLog = ActivityLog.builder()
      .createdAt(LocalDateTime.now())
      .user(user)
      .action("Creating stock entry for id : " + stockEntry.getId())
      .resource(ActivityLogResource.STOCK_ENTRY)
      .detailsBefore(stockEntryResponseDTO.toString())
      .build();
    activityLogRepository.save(activityLog);

    return stockEntryResponseDTO;
  }

  @Transactional
  public StockEntryResponseDTO updateStockEntryById(Long id, UpdateStockEntryRequestDTO stockEntryRequest) {
    // search for the previous stockEntry
    return stockEntryRepository.findById(id)
      .map(
        stockEntry -> {
          String originalStockEntryJson = stockEntryMapper.mapEntityToResponseDTO(stockEntry).toString();

          // for updating Product's stockQuantity
          int updatedStockEntryQuantity = stockEntryRequest.getQuantity() - stockEntry.getQuantity();

          // set updated value into previous stockEntry
          stockEntry.setQuantity(stockEntryRequest.getQuantity());
          stockEntry.setTotalPrice(stockEntryRequest.getTotalPrice());

          // synchronize Product's stockQuantity data in the database
          productRepository.findById(stockEntry.getProduct().getId()).map(
            product -> {
              product.setStockQuantity(product.getStockQuantity() + updatedStockEntryQuantity);
              if(product.getStockQuantity() < 0) {
                throw new InvalidStockQuantityException(
                  "Product : '"+ product.getName()+ "' stock quantity becomes invalid : " + product.getStockQuantity()
                );
              }
              return productRepository.saveAndFlush(product);
            }
          ) .orElseThrow(
            () -> new ProductNotFoundException("There is no product with id : " + stockEntryRequest.getProductId())
          );
          stockEntryRepository.save(stockEntry);

          StockEntryResponseDTO updatedStockEntryResponseDTO = stockEntryMapper.mapEntityToResponseDTO(stockEntry);
          String updatedStockEntryJson = updatedStockEntryResponseDTO.toString();

          // Log update action into ActivityLog
          // fetch detail of user who performed the StockEntry operation
          User user = userRepository.findById(stockEntryRequest.getUserId())
            .orElseThrow(
              () -> new UserNotFoundException("There is no user with id : " + stockEntryRequest.getUserId())
            );

          ActivityLog activityLog = ActivityLog.builder()
            .createdAt(LocalDateTime.now())
            .user(user)
            .action("Updating stock entry for id : " + id)
            .resource(ActivityLogResource.STOCK_ENTRY)
            .detailsBefore(originalStockEntryJson)
            .detailsAfter(updatedStockEntryJson)
            .build();
          activityLogRepository.save(activityLog);

          return updatedStockEntryResponseDTO;
      })
      .orElseThrow(() -> new StockEntryNotFoundException("There is no stock entry with id : " + id));
  }

  @Transactional
  public StockEntryResponseDTO deleteStockEntryById(Long id) {
    StockEntry deletedStockEntry = stockEntryRepository.findById(id)
      .orElseThrow(() -> new StockEntryNotFoundException("There is no stock entry with id : " + id));

    // synchronize Product's stockQuantity data in the database
    Product previousProductData = productRepository.findById(deletedStockEntry.getProduct().getId())
      .map(
        product -> {
          product.setStockQuantity(product.getStockQuantity() - deletedStockEntry.getQuantity());
          if(product.getStockQuantity() < 0) {
            throw new InvalidStockQuantityException(
              "Product : '"+ product.getName()+ "' stock quantity becomes invalid : " + product.getStockQuantity()
            );
          }

          return productRepository.saveAndFlush(product);
        }
      )
      .orElseThrow(
        () -> new ProductNotFoundException("There is no product with id : " + deletedStockEntry.getProduct().getId())
      );

    stockEntryRepository.delete(deletedStockEntry);
    return stockEntryMapper.mapEntityToResponseDTO(deletedStockEntry);
  }

}
