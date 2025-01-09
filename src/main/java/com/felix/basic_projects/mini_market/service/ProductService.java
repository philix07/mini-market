package com.felix.basic_projects.mini_market.service;

import com.felix.basic_projects.mini_market.exception.product.DuplicateProductException;
import com.felix.basic_projects.mini_market.exception.product.ProductNotFoundException;
import com.felix.basic_projects.mini_market.model.Product;
import com.felix.basic_projects.mini_market.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductService {

  @Autowired
  private ProductRepository productRepository;

  public List<Product> retrieveAllProduct() {
    List<Product> products = productRepository.findAll();

    if(products.isEmpty()) {
      throw new ProductNotFoundException("There is no product in this application");
    }

    return products;
  }


  public Product findProductById(Long id) {
    return productRepository.findById(id)
      .orElseThrow(() -> new ProductNotFoundException("There is not product with id : " + id));
  }

  @Transactional
  public List<Product> saveAllProduct(List<Product> products) {
    // Why do we use set?
    // A. we can quickly check if a value exists in a Set using contains().
    // B. searching through set is super quick compared to searching through a List.

    // Check if one of the products have duplicate names in the database
    Set<String> existingName = productRepository.findNamesInSet(
      products.stream()
        .map(Product::getName)
        .toList()
    );

    // Fetch existing barcodes from the database for the incoming product barcodes
    Set<String> existingBarcodes =  productRepository.findBarcodesInSet(
      products.stream()
        .map(Product::getBarcode)
        .toList()
    );

    for (Product product : products) {
      if(existingName.contains(product.getName())) {
        throw new DuplicateProductException("Product with name : " + product.getName() + " already exists");
      } else if (existingBarcodes.contains(product.getBarcode())) {
        throw new DuplicateProductException("Product with barcode : " + product.getBarcode() + " already exists");
      }
    }

    return productRepository.saveAll(products);
  }

  public Product saveProduct(Product product) {
    if(productRepository.existsByName(product.getName())) {
      throw new DuplicateProductException("There is already product with name : '" +product.getName() + "'");
    } else if(productRepository.existsByBarcode(product.getBarcode())) {
      throw new DuplicateProductException("There is already product with barcode : '" +product.getBarcode() + "'");
    }

    return productRepository.save(product);
  }


  public Product deleteProductById(Long id) {
    Product deletedProduct = productRepository.findById(id)
      .orElseThrow(() -> new ProductNotFoundException("There is not product with id : " + id));

    productRepository.delete(deletedProduct);
    return deletedProduct;
  }


  public Product updateProductById(Long id, Product product) {
    return productRepository.findById(id).map(
      newProd -> {
        newProd.setName(product.getName());
        newProd.setBarcode(product.getBarcode());
        newProd.setCategory(product.getCategory());
        newProd.setPrice(product.getPrice());
        newProd.setStockQuantity(product.getStockQuantity());
        return productRepository.save(newProd);
      }
    ).orElseThrow(() -> new ProductNotFoundException("There is not product with id : " + id));
  }
}
