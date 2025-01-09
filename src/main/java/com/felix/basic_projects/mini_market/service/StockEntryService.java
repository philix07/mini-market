package com.felix.basic_projects.mini_market.service;

import com.felix.basic_projects.mini_market.exception.product.InvalidStockQuantityException;
import com.felix.basic_projects.mini_market.exception.product.ProductNotFoundException;
import com.felix.basic_projects.mini_market.exception.stock_entry.StockEntryNotFoundException;
import com.felix.basic_projects.mini_market.model.Product;
import com.felix.basic_projects.mini_market.model.StockEntry;
import com.felix.basic_projects.mini_market.repository.ProductRepository;
import com.felix.basic_projects.mini_market.repository.StockEntryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockEntryService {

  @Autowired
  private StockEntryRepository stockEntryRepository;

  @Autowired
  private ProductRepository productRepository;

  public List<StockEntry> retrieveAllStockEntry() {
    List<StockEntry> stockEntries = stockEntryRepository.findAll();

    if(stockEntries.isEmpty()) {
      throw new StockEntryNotFoundException("There is no stock entry in this application");
    }

    return stockEntries;
  }

  public StockEntry findStockEntryById(Long id) {
    return stockEntryRepository.findById(id)
      .orElseThrow(
        () -> new StockEntryNotFoundException("There is no stock entry with id : " + id)
      );
  }

  @Transactional
  public StockEntry saveStockEntry(StockEntry stockEntry) {

    // synchronize Product's stockQuantity data in the database
    Product updatedProduct = productRepository.findById(stockEntry.getProduct().getId())
      .map(
        product -> {
          product.setStockQuantity(product.getStockQuantity() + stockEntry.getQuantity());
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
        () -> new ProductNotFoundException("There is no product with id : " + stockEntry.getProduct().getId())
      );

    stockEntry.setProduct(updatedProduct);
    return stockEntryRepository.save(stockEntry);
  }

  @Transactional
  public StockEntry updateStockEntryById(Long id, StockEntry stockEntry) {
    // search for the previous stockEntry
    return stockEntryRepository.findById(id)
      .map(
        entry -> {
          // for updating Product's stockQuantity
          int updatedStockEntryQuantity = stockEntry.getQuantity() - entry.getQuantity();

          // set updated value into previous stockEntry
          entry.setQuantity(stockEntry.getQuantity());
          entry.setTotalPrice(stockEntry.getTotalPrice());

          // synchronize Product's stockQuantity data in the database
          productRepository.findById(entry.getProduct().getId()).map(
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
            () -> new ProductNotFoundException("There is no product with id : " + stockEntry.getProduct().getId())
          );

        return stockEntryRepository.save(entry);
      })
      .orElseThrow(() -> new StockEntryNotFoundException("There is no stock entry with id : " + id));
  }

  @Transactional
  public StockEntry deleteStockEntryById(Long id) {
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
    return deletedStockEntry;
  }

}
