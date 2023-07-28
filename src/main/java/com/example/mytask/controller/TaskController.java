package com.example.mytask.controller;

import static com.example.mytask.constant.ServiceRoutePath.*;

import com.example.mytask.config.IntegrationGateway;
import com.example.mytask.dto.TaskUpdateDTO;
import com.example.mytask.model.Task;
import com.example.mytask.dto.TaskDTO;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
  public ResponseEntity editTask(@RequestBody TaskUpdateDTO taskUpdateDTO,
      @PathVariable("id") int id) {
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

  @PostMapping("/{id}/logwork")
  public ResponseEntity addLogwork(
      @RequestParam("timeBegin") String timeBegin,
      @RequestParam("timeEnd") String timeEnd,
      @PathVariable("id") Integer taskId) {
    Map<String, String> map = new HashMap<>();
    map.put("timeBegin", timeBegin);
    map.put("timeEnd", timeEnd);
    map.put("taskId", String.valueOf(taskId));
    return integrationGateway.process(map, LOG_WORK_CHANNEL);
  }

  @PostMapping("/{taskId}/user/{userId}")
  public ResponseEntity assignTaskToUser(@PathVariable("taskId") int taskId,
      @PathVariable("userId") int userId) {
    Map<String, Integer> map = new HashMap<>();
    map.put("userId", userId);
    map.put("taskId", taskId);
    return integrationGateway.process(map, ASSIGN_TASK_CHANNEL);
  }

  @GetMapping("test")
  public ResponseEntity test(@RequestParam("est") int est) {
    Integer deadlineHour = LocalDateTime.now().getHour();
    if (deadlineHour + est >= 17) {
      System.out.println(LocalDate.now().plusDays(1));
      return ResponseEntity.ok("a");
    }
    System.out.println(LocalDate.now());
    return ResponseEntity.ok("a");
  }
}
