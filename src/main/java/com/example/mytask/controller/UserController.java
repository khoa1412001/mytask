package com.example.mytask.controller;

import static com.example.mytask.constant.ServiceRoutePath.*;

import com.example.mytask.config.IntegrationGateway;
import com.example.mytask.dto.UserDTO;
import com.example.mytask.model.User;
import javax.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

  @Autowired
  private IntegrationGateway integrationGateway;

  @GetMapping
  public ResponseEntity<Object> getUsers() {
    return integrationGateway.process(GET_USERS_CHANNEL);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Object> getUser(@PathVariable int id) {
    return integrationGateway.process(id, GET_USER_CHANNEL);
  }

  @PostMapping()
  public ResponseEntity<Object> createUser(@Valid @RequestBody UserDTO userDTO) {
    ModelMapper modelMapper = new ModelMapper();
    User user = modelMapper.map(userDTO, User.class);
    return integrationGateway.process(user, CREATE_USER_CHANNEL);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Object> editUser(@Valid @RequestBody UserDTO userDTO,
      @PathVariable("id") int id) {
    ModelMapper modelMapper = new ModelMapper();
    User user = modelMapper.map(userDTO, User.class);
    user.setId(id);
    return integrationGateway.process(user, EDIT_USER_CHANNEL);
  }

}
