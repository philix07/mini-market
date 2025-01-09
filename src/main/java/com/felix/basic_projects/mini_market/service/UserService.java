package com.felix.basic_projects.mini_market.service;

import com.felix.basic_projects.mini_market.exception.user.UserAlreadyExistException;
import com.felix.basic_projects.mini_market.exception.user.UserNotFoundException;
import com.felix.basic_projects.mini_market.model.User;
import com.felix.basic_projects.mini_market.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

   public List<User> retrieveAllUser() {
    List<User> users = userRepository.findAll();

    if(users.isEmpty())
      throw new UserNotFoundException("There is no user in this application");

    return users;
  }

  public User findUserById(Long id) {
    return userRepository.findById(id)
      .orElseThrow(() -> new UserNotFoundException("There is no user with id : " + id));
  }

  public User saveUser(User user) {
     if(userRepository.existsByEmail(user.getEmail())) {
       throw new UserAlreadyExistException("User with email : '" + user.getEmail() + "' already exist");
     }

     return userRepository.save(user);
  }

  public User deleteUserById(Long id) {
     User user = userRepository.findById(id)
       .orElseThrow(() -> new UserNotFoundException("There is no user with id : " + id));

    userRepository.delete(user);
    return user;
   }

  public User updateUserById(Long id, User newUser) {
     return userRepository.findById(id).map(
       user -> {
         user.setId(id);
         user.setUsername(newUser.getUsername());
         user.setEmail(newUser.getEmail());
         user.setPassword(newUser.getPassword());
         user.setRole(newUser.getRole());
         user.setActive(newUser.isActive());
         return userRepository.save(user);
       }
     ).orElseThrow(() -> new UserNotFoundException("There is no user with id : " + id));
  }

}
