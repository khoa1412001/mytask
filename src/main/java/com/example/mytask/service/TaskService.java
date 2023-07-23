package com.example.mytask.service;

import com.example.mytask.model.Task;
import com.example.mytask.model.User;
import com.example.mytask.payload.DataResponse;
import com.example.mytask.repository.TaskRepository;
import com.example.mytask.repository.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private TaskRepository taskRepository;

  @ServiceActivator(inputChannel = "CREATE_TASK_CHANNEL", outputChannel = "OUTPUT_CHANNEL")
  public DataResponse createTask(Task task) {
    User user = userRepository.findById(0);
    task.setAssignee(user);
    taskRepository.save(task);
    return new DataResponse("Created task successfully");
  }

  @ServiceActivator(inputChannel = "GET_TASK_CHANNEL", outputChannel = "OUTPUT_CHANNEL")
  public DataResponse getTask(int id) {
    Task task = taskRepository.findById(id);
    return new DataResponse(200, "Got task successfully", task);
  }

  @ServiceActivator(inputChannel = "EDIT_TASK_CHANNEL", outputChannel = "OUTPUT_CHANNEL")
  public DataResponse editTask(Task task) {
    taskRepository.save(task);
    return new DataResponse("Updated task successfully");
  }

  @ServiceActivator(inputChannel = "ASSIGN_TASK_CHANNEL", outputChannel = "OUTPUT_CHANNEL")
  public <T> DataResponse assignTask(List<Integer> payload) {
    System.out.println(payload);
    //payload[0] = userId, payload[1] = taskId
    User user = userRepository.findById(payload.get(0).intValue());
    Task task = taskRepository.findById(payload.get(1).intValue());
    task.setAssignee(user);
    taskRepository.save(task);
    return new DataResponse(
        String.format("Assigned task to %s successfully")); //,task.getAssignee()));
  }
}
