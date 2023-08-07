package com.example.mytask.service.impl;

import static com.example.mytask.constant.CONSTANT.CHECK_OUT_TIME;

import com.example.mytask.exceptions.InvalidTimelineException;
import com.example.mytask.exceptions.TaskNotFoundException;
import com.example.mytask.exceptions.UserNotFoundException;
import com.example.mytask.model.Logwork;
import com.example.mytask.model.Task;
import com.example.mytask.model.User;
import com.example.mytask.repository.LogworkRepository;
import com.example.mytask.repository.TaskRepository;
import com.example.mytask.repository.UserRepository;
import com.example.mytask.service.TaskService;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl implements TaskService {

  private static final String NOT_FOUND_TASK = "Task could not be found";
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private TaskRepository taskRepository;
  @Autowired
  private LogworkRepository logworkRepository;

  @Override
  public List<Task> getTasks() {
    return taskRepository.findAll();
  }

  @Override
  public Task createTask(Task task) {
    User user = userRepository.findById(0)
        .orElseThrow(() -> new UserNotFoundException("User could not be found"));
    task.setAssignee(user);
    return taskRepository.save(task);

  }

  @Override
  public Task getTask(int taskId) {
    Task task = taskRepository.findById(taskId)
        .orElseThrow(() -> new TaskNotFoundException(NOT_FOUND_TASK));
    task.setRemaining(calculateRemaining(task.getLogwork(), task.getEst()));
    return task;
  }

  @Override
  public Task editTask(Task updateTask) {
    if (!taskRepository.existsById(updateTask.getId())) {
      throw new TaskNotFoundException(NOT_FOUND_TASK);
    }
    return taskRepository.save(updateTask);
  }

  @Override
  public Task assignTask(int userId, int taskId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new UserNotFoundException("Not found user to assign task"));
    Task task = taskRepository.findById(taskId)
        .orElseThrow(() -> new TaskNotFoundException("Not found task to assign to user"));

    task.setAssignee(user);
    task.setDeadline(localDateToDate(calculateDeadline(task.getEst())));

    return taskRepository.save(task);
  }

  @Override
  public Logwork addLogwork(Timestamp timeBegin, Timestamp timeEnd, int taskId) {
    Task task = taskRepository.findById(taskId)
        .orElseThrow(() -> new TaskNotFoundException(NOT_FOUND_TASK));

    if (timeBegin.compareTo(timeEnd) >= 0) {
      throw new InvalidTimelineException("Invalid timeline");
    }
    Logwork logwork = new Logwork(timeBegin, timeEnd);
    logwork.setTask(task);

    return logworkRepository.save(logwork);
  }

  public LocalDate calculateDeadline(int est) {
    int deadlineHour = LocalDateTime.now().getHour();
    if (deadlineHour + est >= CHECK_OUT_TIME) {
      return LocalDate.now().plusDays(1);
    }
    return LocalDate.now();
  }

  private Date localDateToDate(LocalDate localDate) {
    return Date.valueOf(localDate);
  }

  private String calculateRemaining(List<Logwork> logworkList, Integer est) {
    Long remainingMS = est.longValue() * 3600 * 1000;
    for (Logwork lw : logworkList) {
      remainingMS = remainingMS - (lw.getTimeEnd().getTime() - lw.getTimeStart().getTime());
    }
    if (remainingMS < 0L) {
      return "This task is done";
    }
    String result = "Remaining: ";
    long hours = TimeUnit.MILLISECONDS.toHours(remainingMS);
    result = result.concat(String.format("%s hours ", hours));

    return result;
  }
}


