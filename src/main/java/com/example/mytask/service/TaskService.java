package com.example.mytask.service;

import static com.example.mytask.constant.RoutePath.RESULT_CHANNEL;
import static com.example.mytask.constant.ServiceRoutePath.*;

import com.example.mytask.model.Logwork;
import com.example.mytask.model.Task;
import com.example.mytask.model.User;
import com.example.mytask.repository.LogworkRepository;
import com.example.mytask.repository.TaskRepository;
import com.example.mytask.repository.UserRepository;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
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
  @Autowired
  private LogworkRepository logworkRepository;

  @ServiceActivator(inputChannel = CREATE_TASK_CHANNEL, outputChannel = RESULT_CHANNEL)
  public ResponseEntity createTask(Task task) {
    User user = userRepository.findById(0);
    task.setAssignee(user);
    taskRepository.save(task);
    return new ResponseEntity("Created task successfully", HttpStatus.OK);
  }

  @ServiceActivator(inputChannel = GET_TASK_CHANNEL, outputChannel = RESULT_CHANNEL)
  public ResponseEntity getTask(int id) {
    Task task = taskRepository.findById(id);
    task.setRemaining(calculateRemaining(task.getLogwork(), task.getEst()));
    return new ResponseEntity(task, HttpStatus.OK);
  }

  @ServiceActivator(inputChannel = EDIT_TASK_CHANNEL, outputChannel = RESULT_CHANNEL)
  public ResponseEntity editTask(Task task) {
    taskRepository.save(task);
    return new ResponseEntity("Updated task successfully", HttpStatus.OK);
  }

  @ServiceActivator(inputChannel = ASSIGN_TASK_CHANNEL, outputChannel = RESULT_CHANNEL)
  public ResponseEntity assignTask(Map<String, Integer> payload) {
    User user = userRepository.findById(payload.get("userId").intValue());
    Task task = taskRepository.findById(payload.get("taskId").intValue());
    task.setAssignee(user);
    task.setDeadline(localDateToDate(calculateDeadline(task.getEst())));
    taskRepository.save(task);
    return new ResponseEntity(String.format("Assigned task to %s successfully", task.getAssignee()),
        HttpStatus.OK);
  }

  @ServiceActivator(inputChannel = LOG_WORK_CHANNEL, outputChannel = RESULT_CHANNEL)
  public ResponseEntity addLogwork(Map<String, String> payload) {
    Task task = taskRepository.findById(Integer.parseInt(payload.get("taskId")));
    if (task.getAssignee() == "Undefined") {
      return new ResponseEntity("Must assign task to someone before adding logwork",
          HttpStatus.BAD_REQUEST);
    }
    Logwork logwork = new Logwork();
    logwork.setTimeEnd(Timestamp.valueOf(payload.get("timeBegin")));
    logwork.setTimeStart(Timestamp.valueOf(payload.get("timeEnd")));
    logwork.setTask(task);
    logworkRepository.save(logwork);
//    taskRepository.save(task);
    return ResponseEntity.ok("Add logwork successfully");
  }

  private LocalDate calculateDeadline(Integer est) {
    Integer deadlineHour = LocalDateTime.now().getHour();
    if (deadlineHour + est >= 17) {
      return LocalDate.now().plusDays(1);
    }
    return LocalDate.now();
  }

  private Date localDateToDate(LocalDate localDate) {
    return Date.valueOf(localDate);
  }

  private String calculateRemaining(List<Logwork> logworkList, Integer est) {
    //convert est hour to millisecond
    Long remainingMS = est.longValue() * 3600 * 1000;
    for (Logwork lw : logworkList) {
      remainingMS = remainingMS - (lw.getTimeEnd().getTime() - lw.getTimeStart().getTime());
    }
    if (remainingMS < 0L) {
      return "This task is done";
    }
    String result = "Remaining: ";
    long hours = TimeUnit.MILLISECONDS.toHours(remainingMS);
    long minutes = TimeUnit.MILLISECONDS.toMinutes(remainingMS) % 60;
    long seconds = TimeUnit.MILLISECONDS.toSeconds(remainingMS) % 3600;
    System.out.println(String.format("%s %s %s", hours, minutes, seconds));
    if (hours > 0) {
      result = result.concat(String.format("%s hours ", hours));
    }
    if (minutes > 0) {
      result = result.concat(String.format("%s minutes ", minutes));
    }
    if (seconds > 0) {
      result = result.concat(String.format("%s seconds", seconds));
    }
    return result;
  }
}
