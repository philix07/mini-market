package com.felix.basic_projects.mini_market.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
public class Customer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id; // Primary Key

  @Email
  @NotEmpty(message = "Customer email cannot be empty or null")
  private String email; // Email address

  @NotEmpty(message = "Customer name cannot be empty or null")
  @Size(min = 4, message = "Customer name should have at least 4 character")
  private String name; // Customer name

  @NotEmpty(message = "Contact number cannot be empty or null")
  @Size(min = 10, message = "Contact number should have at least 10 character")
  private String contactNumber; // Contact details

  public Customer() {}

  public Customer(Long id, String email, String name, String contactNumber) {
    this.id = id;
    this.email = email;
    this.name = name;
    this.contactNumber = contactNumber;
  }

  public Customer(String email, String name, String contactNumber) {
    this.email = email;
    this.name = name;
    this.contactNumber = contactNumber;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getContactNumber() {
    return contactNumber;
  }

  public void setContactNumber(String contactNumber) {
    this.contactNumber = contactNumber;
  }

  @Override
  public String toString() {
    return "Customer{" +
      "id=" + id +
      ", email='" + email + '\'' +
      ", name='" + name + '\'' +
      ", contactNumber='" + contactNumber + '\'' +
      '}';
  }
}
