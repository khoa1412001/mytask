package com.example.mytask.service;

import com.example.mytask.model.Task;
import com.example.mytask.model.User;
import com.example.mytask.payload.DataResponse;
import com.example.mytask.repository.TaskRepository;
import com.example.mytask.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    task.assignToAssignee(user);
    taskRepository.save(task);
    return new DataResponse("Created task");
  }

  @ServiceActivator(inputChannel = "GET_TASK_CHANNEL", outputChannel = "OUTPUT_CHANNEL")
  public DataResponse getTask(int id) {
    Task task = taskRepository.findById(id);
    return new DataResponse("Get task succeeded", task);
  }
}
