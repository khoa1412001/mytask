package com.example.mytask.service;

import static com.example.mytask.constant.RoutePath.*;
import static com.example.mytask.constant.ServiceRoutePath.*;

import com.example.mytask.model.User;
import com.example.mytask.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  @ServiceActivator(inputChannel = GET_USER_CHANNEL, outputChannel = RESULT_CHANNEL)
  public ResponseEntity getUser(int id) {
    User user = userRepository.findById(id);
    return new ResponseEntity(user, HttpStatus.OK);
  }

  @ServiceActivator(inputChannel = CREATE_USER_CHANNEL, outputChannel = RESULT_CHANNEL)
  public ResponseEntity createUser(User user) {
    userRepository.save(user);
    return new ResponseEntity<String>("Saved user successfully", HttpStatus.OK);
  }

  @ServiceActivator(inputChannel = EDIT_USER_CHANNEL, outputChannel = RESULT_CHANNEL)
  public ResponseEntity editUser(User user) {
    userRepository.save(user);
    return new ResponseEntity("Updated user successfully", HttpStatus.OK);
  }
}
