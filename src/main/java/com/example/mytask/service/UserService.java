package com.example.mytask.service;

import com.example.mytask.model.User;
import com.example.mytask.payload.DataResponse;
import com.example.mytask.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  @ServiceActivator(inputChannel = "GET_USER_CHANNEL", outputChannel = "OUTPUT_CHANNEL")
  public DataResponse getUser(int id) {
    User user = userRepository.findById(id);
    return new DataResponse("get user success", user);
  }

  @ServiceActivator(inputChannel = "CREATE_USER_CHANNEL", outputChannel = "OUTPUT_CHANNEL")
  public DataResponse createUser(User user) {
    userRepository.save(user);
    return new DataResponse("xui");
  }

  @ServiceActivator(inputChannel = "EDIT_USER_CHANNEL", outputChannel = "OUTPUT_CHANNEL")
  public DataResponse editUser(User user) {
    User user0 = userRepository.findById(0);
    user0.setName("Dang");
    userRepository.save(user0);
    return new DataResponse("yes");
  }
}
