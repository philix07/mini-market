package com.felix.basic_projects.mini_market.mapper;

import com.felix.basic_projects.mini_market.model.dto.response.ActivityLogResponseDTO;
import com.felix.basic_projects.mini_market.model.dto.response.UserResponseDTO;
import com.felix.basic_projects.mini_market.model.entity.ActivityLog;
import com.felix.basic_projects.mini_market.model.entity.User;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {

  /*
    UserResponseDTO's field
      private Long id;
      private String email;
      private String username;
      private UserRole role;
      private boolean isActive;
   */
  public UserResponseDTO mapEntityToResponseDTO(User user) {
    return UserResponseDTO.builder()
      .id(user.getId())
      .email(user.getEmail())
      .username(user.getUsername())
      .role(user.getRole())
      .isActive(user.isActive())
      .build();
  }

}
