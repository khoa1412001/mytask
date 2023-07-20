package com.example.mytask.controller;

import com.example.mytask.config.IntegrationGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/Task")
public class TaskController {

  @Autowired
  private IntegrationGateway integrationGateway;
//  @GetMapping
//  public Task getTask() {
//
//  }
//  @PostMapping
//  public Task editTask() {
//
//  }
}
