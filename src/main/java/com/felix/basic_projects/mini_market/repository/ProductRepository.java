package com.felix.basic_projects.mini_market.repository;

import com.felix.basic_projects.mini_market.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
  boolean existsByName(String name);

  boolean existsByBarcode(String barcode);

  // Custom JPQL Query
  // JPQL is case-sensitive, we must use the class name instead of table name in the query.

  // SELECT name FROM product WHERE name IN ('Name1', 'Name2', ...);
  @Query("SELECT p.name FROM Product p WHERE p.name IN :names")
  Set<String> findNamesInSet(@Param("names") List<String> names);

  // SELECT barcode FROM product WHERE barcode IN ('Barcode1', 'Barcode2', ...);
  @Query("SELECT p.barcode FROM Product p WHERE p.barcode IN :barcodes")
  Set<String> findBarcodesInSet(@Param("barcodes") List<String> barcodes);
}
