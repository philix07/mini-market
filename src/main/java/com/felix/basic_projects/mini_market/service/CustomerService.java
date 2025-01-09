package com.felix.basic_projects.mini_market.service;

import com.felix.basic_projects.mini_market.exception.customer.CustomerNotFoundException;
import com.felix.basic_projects.mini_market.model.Customer;
import com.felix.basic_projects.mini_market.repository.CustomerRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

  @Autowired
  private CustomerRepository customerRepository;

  public List<Customer> retrieveAllCustomer() {
    List<Customer> customers = customerRepository.findAll();

    if(customers.isEmpty()) {
      throw new CustomerNotFoundException("There is no customer in this application");
    }

    return customers;
  }

  public Customer findCustomerById(Long id) {
    return customerRepository.findById(id)
      .orElseThrow(() -> new CustomerNotFoundException("There is no customer with id : " + id));
  }

  public Customer saveCustomer(Customer customer) {
    if(customerRepository.existsByEmail(customer.getEmail())) {
      throw new CustomerNotFoundException("Customer with email : '" + customer.getEmail() + "' is already registered");
    } else if (customerRepository.existsByContactNumber(customer.getContactNumber())) {
      throw new CustomerNotFoundException("Customer with phone number : '" + customer.getEmail() + "' is already registered");
    }

    return customerRepository.save(customer);
  }

  public Customer deleteCustomerById(Long id) {
    Customer customer = customerRepository.findById(id)
      .orElseThrow(() -> new CustomerNotFoundException("There is no customer with id : " + id));

    customerRepository.delete(customer);
    return customer;
  }

  public Customer updateCustomerById(Long id, Customer customer) {
    return customerRepository.findById(id)
      .map(cust -> {
        cust.setName(customer.getName());
        cust.setEmail(customer.getEmail());
        cust.setContactNumber(customer.getContactNumber());
        return customerRepository.save(cust);
      })
      .orElseThrow(() -> new CustomerNotFoundException("There is no customer with id : " + id));
  }


}
