package com.example.mytask.controller;

import com.example.mytask.service.IntegrationGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.mytask.model.User;

@RestController
@RequestMapping("api/v1/User")
public class UserController {

  @Autowired
  private IntegrationGateway integrationGateway;
//  @PostMapping
//  public void addUser(@RequestBody User user) {
//
//  }

  @GetMapping()
  public User getUser() {
    User user = new User();
    System.out.println(user.toString());
    return integrationGateway.invoke(user, "GET_USER");
  }

  @PostMapping()
  public User createUser() {

  }

  @PostMapping()
  public User editUser() {

  }

}
