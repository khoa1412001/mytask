package com.example.mytask.service;

import static com.example.mytask.constant.ServiceRoutePath.*;

import com.example.mytask.model.Task;
import com.example.mytask.model.User;
import com.example.mytask.repository.TaskRepository;
import com.example.mytask.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private TaskRepository taskRepository;

  @ServiceActivator(inputChannel = "CREATE_TASK_CHANNEL", outputChannel = "OUTPUT_CHANNEL")
  public ResponseEntity createTask(Task task) {
    User user = userRepository.findById(0);
    task.setAssignee(user);
    taskRepository.save(task);
    return new ResponseEntity("Created task successfully", HttpStatus.OK);
  }

  @ServiceActivator(inputChannel = "GET_TASK_CHANNEL", outputChannel = "OUTPUT_CHANNEL")
  public ResponseEntity getTask(int id) {
    Task task = taskRepository.findById(id);
    //"Got task successfully",
    return new ResponseEntity(task, HttpStatus.OK);
  }

  @ServiceActivator(inputChannel = "EDIT_TASK_CHANNEL", outputChannel = "OUTPUT_CHANNEL")
  public ResponseEntity editTask(Task task) {
    taskRepository.save(task);
    return new ResponseEntity("Updated task successfully", HttpStatus.OK);
  }

  @ServiceActivator(inputChannel = "ASSIGN_TASK_CHANNEL", outputChannel = "OUTPUT_CHANNEL")
  public <T> ResponseEntity assignTask(Map<String, Integer> payload) {
    System.out.println(payload);
    //payload[0] = userId, payload[1] = taskId
    User user = userRepository.findById(payload.get("userId").intValue());
    Task task = taskRepository.findById(payload.get("taskId").intValue());
    task.setAssignee(user);
    calculateDeadline(task.getEst());
    taskRepository.save(task);
    return new ResponseEntity(String.format("Assigned task to %s successfully", task.getAssignee()),
        HttpStatus.OK);
  }

  private Date calculateDeadline(Integer est) {
    Integer deadlineHour = LocalDateTime.now().getHour();
    System.out.println(LocalDateTime.now().plusDays(1));
    return new Date();
  }
}
