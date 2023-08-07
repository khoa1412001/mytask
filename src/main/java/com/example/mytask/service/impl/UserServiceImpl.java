package com.example.mytask.service.impl;

import com.example.mytask.exceptions.UserNotFoundException;
import com.example.mytask.model.User;
import com.example.mytask.repository.UserRepository;
import com.example.mytask.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public List<User> getUsers() {
    List<User> userList = userRepository.findAll();
    userList.remove(0);
    return userList;
  }

  public User getUser(int id) {
    return userRepository.findById(id)
        .orElseThrow(() -> new UserNotFoundException("User could not be found"));
  }

  @Override
  public User createUser(User user) {
    return userRepository.save(user);
  }

  @Override
  public User editUser(User updateUser) {
    if (!userRepository.existsById(updateUser.getId())) {
      throw new UserNotFoundException("User could not be found");
    }
    return userRepository.save(updateUser);
  }
}
