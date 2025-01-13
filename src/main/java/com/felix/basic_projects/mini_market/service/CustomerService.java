package com.felix.basic_projects.mini_market.service;

import com.felix.basic_projects.mini_market.exception.customer.CustomerNotFoundException;
import com.felix.basic_projects.mini_market.mapper.CustomerMapper;
import com.felix.basic_projects.mini_market.model.dto.request.CreateCustomerRequestDTO;
import com.felix.basic_projects.mini_market.model.dto.request.UpdateCustomerRequestDTO;
import com.felix.basic_projects.mini_market.model.dto.response.CustomerResponseDTO;
import com.felix.basic_projects.mini_market.model.entity.Customer;
import com.felix.basic_projects.mini_market.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

  @Autowired
  private CustomerRepository customerRepository;

  @Autowired
  private CustomerMapper customerMapper;

  public List<CustomerResponseDTO> retrieveAllCustomer() {
    List<Customer> customers = customerRepository.findAll();

    if(customers.isEmpty()) {
      throw new CustomerNotFoundException("There is no customer in this application");
    }

    return customers.stream().map(customerMapper::mapEntityToResponseDTO).toList();
  }

  public CustomerResponseDTO findCustomerById(Long id) {
    return customerRepository.findById(id).map(customerMapper::mapEntityToResponseDTO)
      .orElseThrow(() -> new CustomerNotFoundException("There is no customer with id : " + id));
  }

  public CustomerResponseDTO saveCustomer(CreateCustomerRequestDTO request) {
    if(customerRepository.existsByEmail(request.getEmail())) {
      throw new CustomerNotFoundException("Customer with email : '" + request.getEmail() + "' is already registered");
    } else if (customerRepository.existsByContactNumber(request.getContactNumber())) {
      throw new CustomerNotFoundException("Customer with phone number : '" + request.getContactNumber() + "' is already registered");
    }

    Customer customer = Customer.builder()
      .name(request.getName())
      .email(request.getEmail())
      .contactNumber(request.getContactNumber())
      .build();
    customerRepository.save(customer);

    return customerMapper.mapEntityToResponseDTO(customer);
  }

  public CustomerResponseDTO deleteCustomerById(Long id) {
    Customer customer = customerRepository.findById(id)
      .orElseThrow(() -> new CustomerNotFoundException("There is no customer with id : " + id));

    customerRepository.delete(customer);
    return customerMapper.mapEntityToResponseDTO(customer);
  }

  public CustomerResponseDTO updateCustomerById(Long id, UpdateCustomerRequestDTO customer) {
    return customerRepository.findById(id)
      .map(newCustomer -> {
        newCustomer.setName(customer.getName());
        newCustomer.setEmail(customer.getEmail());
        newCustomer.setContactNumber(customer.getContactNumber());
        customerRepository.save(newCustomer); // Save the new data
        return customerMapper.mapEntityToResponseDTO(newCustomer);
      })
      .orElseThrow(() -> new CustomerNotFoundException("There is no customer with id : " + id));
  }

}
