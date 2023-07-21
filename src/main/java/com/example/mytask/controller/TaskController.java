package com.example.mytask.controller;

import com.example.mytask.config.IntegrationGateway;
import com.example.mytask.model.Task;
import com.example.mytask.model.User;
import com.example.mytask.model.dto.TaskDTO;
import com.example.mytask.payload.DataResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/Task")
public class TaskController {

  @Autowired
  private IntegrationGateway integrationGateway;

  @GetMapping("/{id}")
  public DataResponse getTask(@PathVariable("id") Integer id) {
    return integrationGateway.invoke(id, "GET_TASK_CHANNEL");
  }

  @PostMapping("/edit/{id}")
  public Task editTask(@RequestBody TaskDTO taskDTO, @PathVariable("id") int id) {
    ModelMapper modelMapper = new ModelMapper();
    
  }

  @PostMapping("create")
  public DataResponse createTask(@RequestBody TaskDTO taskDTO) {
    ModelMapper modelMapper = new ModelMapper();
    Task task = modelMapper.map(taskDTO, Task.class);
    return integrationGateway.invoke(task, "CREATE_TASK");
  }
}
