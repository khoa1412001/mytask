package com.example.mytask.service;

import com.example.mytask.model.User;
import java.util.List;

public interface UserService {

  List<User> getUsers();

  User getUser(int id);

  User createUser(User user);

  User editUser(User user);
}
