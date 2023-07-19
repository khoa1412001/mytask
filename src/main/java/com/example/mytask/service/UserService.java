package com.example.mytask.service;

import com.example.mytask.model.User;
import java.util.List;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @ServiceActivator(inputChannel = "GET_USER_CHANNEL", outputChannel = "OUTPUT_CHANNEL")
  public <T> T getUser(T test) {
    return test;
  }
}
