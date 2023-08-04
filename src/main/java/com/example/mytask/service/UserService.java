package com.example.mytask.service;

import com.example.mytask.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;

public interface UserService {

  List<User> getUsers();

  Optional<User> getUser(Integer id);

  String createUser(User user);

  String editUser(User user);
}
