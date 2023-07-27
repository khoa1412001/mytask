package com.example.mytask.controller;

import static com.example.mytask.constant.ServiceRoutePath.*;

import com.example.mytask.config.IntegrationGateway;
import com.example.mytask.dto.TaskUpdateDTO;
import com.example.mytask.model.Task;
import com.example.mytask.dto.TaskDTO;
import java.util.HashMap;
import java.util.Map;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/task")
public class TaskController {

  @Autowired
  private IntegrationGateway integrationGateway;

  @GetMapping("/{id}")
  public ResponseEntity getTask(@PathVariable("id") Integer id) {
    return integrationGateway.process(id, GET_TASK_CHANNEL);
  }

  @PutMapping("/{id}")
  public ResponseEntity editTask(@RequestBody TaskUpdateDTO taskUpdateDTO, @PathVariable("id") int id) {
    ModelMapper modelMapper = new ModelMapper();
    Task task = modelMapper.map(taskUpdateDTO, Task.class);
    task.setId(id);
    return integrationGateway.process(task, EDIT_TASK_CHANNEL);
  }

  @PostMapping()
  public ResponseEntity createTask(@RequestBody TaskDTO taskDTO) {
    ModelMapper modelMapper = new ModelMapper();
    Task task = modelMapper.map(taskDTO, Task.class);
    return integrationGateway.process(task, CREATE_TASK_CHANNEL);
  }

  @PostMapping("/logwork")
  public ResponseEntity addLogwork(@RequestBody ){}

  @PostMapping("/{taskId}/user/{userId}")
  public ResponseEntity assignTaskToUser(@PathVariable("taskId") int taskId, @PathVariable("userId") int userId) {
    Map<String,Integer> map = new HashMap<>();
    map.put("userId", userId);
    map.put("taskId", taskId);
    return integrationGateway.process(map, ASSIGN_TASK_CHANNEL);
  }

  @GetMapping("test")
  public ResponseEntity test(@RequestParam("role") String role) {
//    throw new RuntimeException("custome error");
//    Task task;
//    task.new = "hello";
    System.out.println();
    //    for (Role r :Role.values()) {
//      r.label.equals(role) {
//        return true
//      }
//    }
//    return integrationGateway.invoke("test payload", TEST_CHANNEL);
    return ResponseEntity.ok("a");
  }
}
