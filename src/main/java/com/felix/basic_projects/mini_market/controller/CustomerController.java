package com.felix.basic_projects.mini_market.controller;

import com.felix.basic_projects.mini_market.model.Customer;
import com.felix.basic_projects.mini_market.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1")
public class CustomerController {

  @Autowired
  private CustomerService customerService;

  @GetMapping("customers")
  public ResponseEntity<List<Customer>> retrieveAllCustomer() {
    List<Customer> customers = customerService.retrieveAllCustomer();
    return ResponseEntity.ok(customers);
  }

  @GetMapping("customers/{id}")
  public ResponseEntity<Customer> findCustomerById(@PathVariable Long id) {
    Customer customer = customerService.findCustomerById(id);
    return ResponseEntity.ok(customer);
  }

  @PostMapping("customers")
  public ResponseEntity<Customer> saveCustomer(@Valid @RequestBody Customer customer) {
    Customer newCustomer = customerService.saveCustomer(customer);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
      .path("/{id}")
      .buildAndExpand(customer.getId())
      .toUri();

    return ResponseEntity.created(location).body(newCustomer);
  }

  @DeleteMapping("customers/{id}")
  public ResponseEntity<Customer> deleteCustomerById(@PathVariable Long id) {
    Customer deletedCustomer = customerService.deleteCustomerById(id);
    return ResponseEntity.ok(deletedCustomer);
  }

  @PatchMapping("customers/{id}")
  public ResponseEntity<Customer> updateCustomerById(@PathVariable Long id, @Valid @RequestBody Customer customer) {
    Customer updatedCustomer = customerService.updateCustomerById(id, customer);
    return ResponseEntity.ok(updatedCustomer);
  }
}
