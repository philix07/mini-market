package com.felix.basic_projects.mini_market.service;

import com.felix.basic_projects.mini_market.exception.user.UserAlreadyExistException;
import com.felix.basic_projects.mini_market.exception.user.UserNotFoundException;
import com.felix.basic_projects.mini_market.mapper.ActivityLogMapper;
import com.felix.basic_projects.mini_market.mapper.UserMapper;
import com.felix.basic_projects.mini_market.model.dto.request.CreateUserRequestDTO;
import com.felix.basic_projects.mini_market.model.dto.request.UpdateUserRequestDTO;
import com.felix.basic_projects.mini_market.model.dto.response.UserResponseDTO;
import com.felix.basic_projects.mini_market.model.entity.ActivityLog;
import com.felix.basic_projects.mini_market.model.entity.User;
import com.felix.basic_projects.mini_market.model.entity.enums.ActivityLogResource;
import com.felix.basic_projects.mini_market.repository.ActivityLogRepository;
import com.felix.basic_projects.mini_market.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private UserMapper userMapper;

  @Autowired
  private ActivityLogRepository logRepository;
  @Autowired
  private ActivityLogMapper logMapper;

  public List<UserResponseDTO> retrieveAllUser() {
    List<User> users = userRepository.findAll();

    if(users.isEmpty())
      throw new UserNotFoundException("There is no user in this application");

    return users.stream().map(userMapper::mapEntityToResponseDTO).toList();
  }

  public UserResponseDTO findUserById(Long id) {
    return userRepository.findById(id).map(userMapper::mapEntityToResponseDTO)
      .orElseThrow(() -> new UserNotFoundException("There is no user with id : " + id));
  }

  public UserResponseDTO saveUser(CreateUserRequestDTO request) {
     if(userRepository.existsByEmail(request.getEmail())) {
       throw new UserAlreadyExistException("User with email : '" + request.getEmail() + "' already exist");
     }

    User user = User.builder()
      .username(request.getUsername())
      .email(request.getEmail())
      .password(request.getPassword())
      .role(request.getRole())
      .isActive(true)
      .build();
     userRepository.save(user);

     return userMapper.mapEntityToResponseDTO(user);
  }

  public UserResponseDTO deleteUserById(Long id) {
     User user = userRepository.findById(id)
       .orElseThrow(() -> new UserNotFoundException("There is no user with id : " + id));

    userRepository.delete(user);
    return userMapper.mapEntityToResponseDTO(user);
   }

  public UserResponseDTO updateUserById(Long id, UpdateUserRequestDTO newUser) {
     return userRepository.findById(id)
       .map(
         user -> {
           String originalUserJson = userMapper.mapEntityToResponseDTO(user).toString();

           user.setUsername(newUser.getUsername());
           user.setEmail(newUser.getEmail());
           user.setPassword(newUser.getPassword());
           user.setRole(newUser.getRole());
           user.setActive(newUser.isActive());
           userRepository.save(user);

           UserResponseDTO userResponseDTO = userMapper.mapEntityToResponseDTO(user);
           String updatedUserJson = userResponseDTO.toString();

           // fetch user details
           User actionPerformer = userRepository.findById(newUser.getUserId())
             .orElseThrow(() -> new UserNotFoundException("There is no user with id : " + newUser.getUserId()));

           ActivityLog log = ActivityLog.builder()
             .createdAt(LocalDateTime.now())
             .user(actionPerformer)
             .action("Updating user with id : " + id)
             .resource(ActivityLogResource.USER)
             .detailsBefore(originalUserJson)
             .detailsAfter(updatedUserJson)
             .build();
           logRepository.save(log);

           return userResponseDTO;
         }
       )

       .orElseThrow(() -> new UserNotFoundException("There is no user with id : " + id));
  }

}
