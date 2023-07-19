package com.example.mytask.controller;

import com.example.mytask.model.Task;
import com.example.mytask.repository.UserRepository;
import com.example.mytask.service.IntegrationGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/Task")
public class TaskController {
  @Autowired
  private IntegrationGateway integrationGateway;
  @GetMapping
  public Task getTask() {

  }
  @PostMapping
  public Task editTask() {

  }
}
