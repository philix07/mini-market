package com.felix.basic_projects.mini_market.service;

import com.felix.basic_projects.mini_market.exception.product.DuplicateProductException;
import com.felix.basic_projects.mini_market.exception.product.ProductNotFoundException;
import com.felix.basic_projects.mini_market.exception.user.UserNotFoundException;
import com.felix.basic_projects.mini_market.mapper.ActivityLogMapper;
import com.felix.basic_projects.mini_market.mapper.ProductMapper;
import com.felix.basic_projects.mini_market.model.dto.request.CreateProductRequestDTO;
import com.felix.basic_projects.mini_market.model.dto.request.UpdateProductRequestDTO;
import com.felix.basic_projects.mini_market.model.dto.response.ProductResponseDTO;
import com.felix.basic_projects.mini_market.model.entity.ActivityLog;
import com.felix.basic_projects.mini_market.model.entity.Product;
import com.felix.basic_projects.mini_market.model.entity.User;
import com.felix.basic_projects.mini_market.model.entity.enums.ActivityLogResource;
import com.felix.basic_projects.mini_market.model.entity.enums.ProductCategory;
import com.felix.basic_projects.mini_market.repository.ActivityLogRepository;
import com.felix.basic_projects.mini_market.repository.ProductRepository;
import com.felix.basic_projects.mini_market.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
public class ProductService {

  @Autowired
  private ProductRepository productRepository;
  @Autowired
  private ProductMapper productMapper;

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private ActivityLogRepository logRepository;
  @Autowired
  private ActivityLogMapper logMapper;

  public List<ProductResponseDTO> retrieveAllProduct() {
    List<Product> products = productRepository.findAll();

    if(products.isEmpty()) {
      throw new ProductNotFoundException("There is no product in this application");
    }

    return products.stream().map(productMapper::mapEntityToResponseDTO).toList();
  }


  public ProductResponseDTO findProductById(Long id) {
    return productRepository.findById(id).map(product -> productMapper.mapEntityToResponseDTO(product))
      .orElseThrow(() -> new ProductNotFoundException("There is not product with id : " + id));
  }

  @Transactional
  public List<ProductResponseDTO> saveAllProduct(List<CreateProductRequestDTO> productsRequest) {
    // Why do we use set?
    // A. we can quickly check if a value exists in a Set using contains().
    // B. searching through set is super quick compared to searching through a List.

    // Map the CreateProductRequestDTO into Product Entity first.
    List<Product> products = productsRequest.stream().map(
      request -> Product.builder()
        .name(request.getName())
        .barcode(request.getBarcode())
        .category(ProductCategory.fromString(request.getCategory()))
        .price(request.getPrice())
        .stockQuantity(request.getStockQuantity())
        .build()
    ).toList();

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

    productRepository.saveAll(products);
    return products.stream().map(productMapper::mapEntityToResponseDTO).toList();
  }

  public ProductResponseDTO saveProduct(CreateProductRequestDTO request) {
    if(productRepository.existsByName(request.getName())) {
      throw new DuplicateProductException("There is already product with name : '" + request.getName() + "'");
    } else if(productRepository.existsByBarcode(request.getBarcode())) {
      throw new DuplicateProductException("There is already product with barcode : '" + request.getBarcode() + "'");
    }

    Product product = Product.builder()
      .name(request.getName())
      .barcode(request.getBarcode())
      .category(ProductCategory.fromString(request.getCategory()))
      .price(request.getPrice())
      .stockQuantity(request.getStockQuantity())
      .build();

    productRepository.save(product);
    return productMapper.mapEntityToResponseDTO(product);
  }


  public ProductResponseDTO deleteProductById(Long id) {
    Product deletedProduct = productRepository.findById(id)
      .orElseThrow(() -> new ProductNotFoundException("There is not product with id : " + id));

    productRepository.delete(deletedProduct);
    return productMapper.mapEntityToResponseDTO(deletedProduct);
  }


  public ProductResponseDTO updateProductById(Long id, UpdateProductRequestDTO request) {
    return productRepository.findById(id)
      .map(
      newProd -> {
        String originalProductJson = productMapper.mapEntityToResponseDTO(newProd).toString();

        newProd.setName(request.getName());
        newProd.setBarcode(request.getBarcode());
        newProd.setCategory(ProductCategory.fromString(request.getCategory()));
        newProd.setPrice(request.getPrice());
        productRepository.save(newProd);

        ProductResponseDTO productResponseDTO = productMapper.mapEntityToResponseDTO(newProd);
        String updateProductJson = productResponseDTO.toString();

        User user = userRepository.findById(request.getUserId())
          .orElseThrow(
            () -> new UserNotFoundException("There is no user with id : " + request.getUserId())
          );

        ActivityLog log = ActivityLog.builder()
          .createdAt(LocalDateTime.now())
          .user(user)
          .action("Updating product data with id : " + id)
          .resource(ActivityLogResource.PRODUCT)
          .detailsBefore(originalProductJson)
          .detailsAfter(updateProductJson)
          .build();
        logRepository.save(log);

        return productResponseDTO;
      }
      )
      .orElseThrow(() -> new ProductNotFoundException("There is not product with id : " + id));
  }
}
