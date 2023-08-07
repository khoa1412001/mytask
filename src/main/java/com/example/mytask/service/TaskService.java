package com.example.mytask.service;

import com.example.mytask.model.Logwork;
import com.example.mytask.model.Task;
import java.sql.Timestamp;
import java.util.List;

public interface TaskService {

  public List<Task> getTasks();

  public Task createTask(Task task);

  public Task getTask(int taskId);

  public Task editTask(Task task);

  public Task assignTask(int userId, int taskId);

  public Logwork addLogwork(Timestamp timeBegin, Timestamp timeEnd, int taskId);

}
