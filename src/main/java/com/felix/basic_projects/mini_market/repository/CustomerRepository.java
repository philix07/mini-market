package com.felix.basic_projects.mini_market.repository;

import com.felix.basic_projects.mini_market.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
  boolean existsByEmail(String email);

  boolean existsByContactNumber(String contactNumber);
}
