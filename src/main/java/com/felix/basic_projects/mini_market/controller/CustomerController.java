package com.felix.basic_projects.mini_market.controller;

import com.felix.basic_projects.mini_market.mapper.CustomerMapper;
import com.felix.basic_projects.mini_market.model.dto.request.CreateCustomerRequestDTO;
import com.felix.basic_projects.mini_market.model.dto.request.UpdateCustomerRequestDTO;
import com.felix.basic_projects.mini_market.model.dto.response.CustomerResponseDTO;
import com.felix.basic_projects.mini_market.model.entity.Customer;
import com.felix.basic_projects.mini_market.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1")
public class CustomerController {

  private final CustomerService customerService;

  public CustomerController(CustomerService customerService) {
    this.customerService = customerService;
  }

  @GetMapping("customers")
  public ResponseEntity<List<CustomerResponseDTO>> retrieveAllCustomer() {
    return ResponseEntity.ok(customerService.retrieveAllCustomer());
  }

  @GetMapping("customers/{id}")
  public ResponseEntity<CustomerResponseDTO> findCustomerById(@PathVariable Long id) {
    return ResponseEntity.ok(customerService.findCustomerById(id));
  }

  @PostMapping("customers")
  public ResponseEntity<CustomerResponseDTO> saveCustomer(@Valid @RequestBody CreateCustomerRequestDTO customer) {
    CustomerResponseDTO createdCustomer = customerService.saveCustomer(customer);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
      .path("/{id}")
      .buildAndExpand(createdCustomer.getId())
      .toUri();

    return ResponseEntity.created(location).body(createdCustomer);
  }

  @DeleteMapping("customers/{id}")
  public ResponseEntity<CustomerResponseDTO> deleteCustomerById(@PathVariable Long id) {
    return ResponseEntity.ok(customerService.deleteCustomerById(id));
  }

  @PatchMapping("customers/{id}")
  public ResponseEntity<CustomerResponseDTO> updateCustomerById(
    @PathVariable Long id,
    @Valid @RequestBody UpdateCustomerRequestDTO customer
  ) {
    return ResponseEntity.ok(customerService.updateCustomerById(id, customer));
  }
}
