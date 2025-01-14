package com.felix.basic_projects.mini_market.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.felix.basic_projects.mini_market.model.entity.enums.UserRole;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateUserRequestDTO {

  @NotNull(message = "userId for the one who triggers the updates must provided")
  private Long userId;

  @Email
  @NotEmpty(message = "User email cannot be empty or null")
  private String email;

  @NotEmpty(message = "Username cannot be empty or null")
  @Size(min = 4, message = "Username should have at least 4 character")
  private String username;

  //TODO: When we fetch the users data, this field is supposed to be hidden
  @NotEmpty(message = "Password cannot be empty or null")
  @Size(min = 8, message = "Password should have at least 8 character")
  private String password;

  @NotNull(message = "User role cannot be empty or null")
  private UserRole role;

  @JsonProperty("isActive")
  @NotNull(message = "User status cannot be null")
  private boolean isActive;

}
