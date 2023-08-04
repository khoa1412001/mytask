package com.example.mytask.service.serviceImpl;

import com.example.mytask.model.User;
import com.example.mytask.repository.UserRepository;
import com.example.mytask.service.UserService;
import java.util.List;
import java.util.Optional;
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

  public Optional<User> getUser(Integer id) {
    return userRepository.findById(id);
  }

  @Override
  public String createUser(User user) {
    userRepository.save(user);
    return "Saved user successfully";
  }

  @Override
  public String editUser(User user) {
    userRepository.save(user);
    return "Updated user successfully";
  }
}
