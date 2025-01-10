package com.felix.basic_projects.mini_market.controller;

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

  @Autowired
  private UserService service;

  @GetMapping("users")
  public ResponseEntity<List<User>> retrieveAllUser() {
    List<User> users = service.retrieveAllUser();
    return ResponseEntity.ok().body(users);
  }

  @GetMapping("users/{id}")
  public ResponseEntity<User> findUserById(@PathVariable Long id) {
    User user = service.findUserById(id);
    return ResponseEntity.ok().body(user);
  }

  @PostMapping("users")
  public ResponseEntity<User> createNewUser(@Valid @RequestBody User user) {
    service.saveUser(user);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
      .path("/{id}")
      .buildAndExpand(user.getId())
      .toUri();

    return ResponseEntity.created(location).body(user);
  }

  @DeleteMapping("users/{id}")
  public ResponseEntity<User> deleteUserById(@PathVariable Long id) {
    User deletedUser = service.deleteUserById(id);
    return ResponseEntity.ok(deletedUser);
  }

  @PatchMapping("users/{id}")
  public ResponseEntity<User> updateUserById(@PathVariable Long id, @Valid @RequestBody User user) {
    User updatedUser = service.updateUserById(id, user);
    return ResponseEntity.ok(updatedUser);
  }

}







