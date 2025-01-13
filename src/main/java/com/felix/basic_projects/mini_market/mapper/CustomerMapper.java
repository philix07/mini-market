package com.felix.basic_projects.mini_market.mapper;

import com.felix.basic_projects.mini_market.model.dto.response.CustomerResponseDTO;
import com.felix.basic_projects.mini_market.model.entity.Customer;
import org.springframework.stereotype.Service;

@Service
public class CustomerMapper {

  /*
    CustomerResponseDTO's field
      private Long id;
      private String email;
      private String name;
      private String contactNumber;
  */
  public CustomerResponseDTO mapEntityToResponseDTO(Customer customer) {
    return CustomerResponseDTO.builder()
      .id(customer.getId())
      .email(customer.getEmail())
      .name(customer.getName())
      .contactNumber(customer.getContactNumber())
      .build();
  }

}
