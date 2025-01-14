package com.felix.basic_projects.mini_market.controller;

import com.felix.basic_projects.mini_market.model.dto.request.CreateUserRequestDTO;
import com.felix.basic_projects.mini_market.model.dto.request.UpdateUserRequestDTO;
import com.felix.basic_projects.mini_market.model.dto.response.UserResponseDTO;
import com.felix.basic_projects.mini_market.model.entity.User;
import com.felix.basic_projects.mini_market.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("v1")
public class UserController {

  private final UserService service;

  public UserController(UserService service) {
    this.service = service;
  }

  @GetMapping("users")
  public ResponseEntity<List<UserResponseDTO>> retrieveAllUser() {
    return ResponseEntity.ok().body(service.retrieveAllUser());
  }

  @GetMapping("users/{id}")
  public ResponseEntity<UserResponseDTO> findUserById(@PathVariable Long id) {
    return ResponseEntity.ok().body(service.findUserById(id));
  }

  @PostMapping("users")
  public ResponseEntity<UserResponseDTO> createNewUser(@Valid @RequestBody CreateUserRequestDTO user) {
    UserResponseDTO userResponseDTO = service.saveUser(user);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
      .path("/{id}")
      .buildAndExpand(userResponseDTO.getId())
      .toUri();

    return ResponseEntity.created(location).body(userResponseDTO);
  }

  @DeleteMapping("users/{id}")
  public ResponseEntity<UserResponseDTO> deleteUserById(@PathVariable Long id) {
    return ResponseEntity.ok(service.deleteUserById(id));
  }

  @PatchMapping("users/{id}")
  public ResponseEntity<UserResponseDTO> updateUserById(
    @PathVariable Long id,
    @Valid @RequestBody UpdateUserRequestDTO user
  ) {
    return ResponseEntity.ok(service.updateUserById(id, user));
  }

}







