package com.example.mytask.service;

import com.example.mytask.model.Logwork;
import com.example.mytask.model.Task;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;

public interface TaskService {

  public List<Task> getTasks();

  public String createTask(Task task);

  public Optional<Task> getTask(Integer taskId);

  public String editTask(Task task);

  public String assignTask(int userId, int taskId);

  public String addLogwork(Timestamp timeBegin, Timestamp timeEnd, Integer taskId);

}
