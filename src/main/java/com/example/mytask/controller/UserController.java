package com.example.mytask.controller;

import com.example.mytask.model.dto.UserDTO;
import com.example.mytask.payload.DataResponse;
import com.example.mytask.config.IntegrationGateway;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.mytask.model.User;

@RestController
@RequestMapping("api/v1/User")
public class UserController {

  @Autowired
  private IntegrationGateway integrationGateway;

  @GetMapping("/{id}")
  public DataResponse getUser(@PathVariable int id) {
    return integrationGateway.invoke(id, "GET_USER");
  }

  @PostMapping("/create")
  public DataResponse createUser(@RequestBody UserDTO userDTO) {
    ModelMapper modelMapper = new ModelMapper();
    User user = modelMapper.map(userDTO, User.class);
    return integrationGateway.invoke(user, "CREATE_USER");
  }

  @PostMapping("/edit/{id}")
  public DataResponse editUser(@RequestBody UserDTO userDTO, @PathVariable("id") int id) {
    ModelMapper modelMapper = new ModelMapper();
    User user = modelMapper.map(userDTO, User.class);
    user.setId(id);
    return integrationGateway.invoke(user, "EDIT_USER");
  }

}
