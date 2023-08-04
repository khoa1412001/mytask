package com.example.mytask.service.serviceImpl;

import static com.example.mytask.constant.CONSTANT.CHECK_OUT_TIME;
import static com.example.mytask.constant.CONSTANT.UNDEFINED;

import com.example.mytask.constant.CONSTANT;
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
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl implements TaskService {

  private static Logger logger = LogManager.getLogger();

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
  public String createTask(Task task) {
    try {
      Optional<User> user = userRepository.findById(0);
      task.setAssignee(user.get());
      taskRepository.save(task);
      return "Created task successfully";
    } catch (Exception e) {
      logger.error(e.getMessage());
      return e.getMessage();
    }
  }

  @Override
  public Optional<Task> getTask(Integer taskId) {
    Optional<Task> task = taskRepository.findById(taskId);
    task.ifPresent(t -> t.setRemaining(calculateRemaining(t.getLogwork(), t.getEst())));
    return task;
  }

  @Override
  public String editTask(Task task) {
    taskRepository.save(task);
    return "Updated task successfully";
  }

  @Override
  public String assignTask(int userId, int taskId) {
    Optional<User> u = userRepository.findById(userId);
    if (u.isEmpty()) {
      return "Not found user to assign task";
    }

    Optional<Task> t = taskRepository.findById(taskId);
    if (t.isEmpty()) {
      return "Not found task to assign to user";
    }

    User user = u.get();
    Task task = t.get();

    task.setAssignee(user);
    task.setDeadline(localDateToDate(calculateDeadline(task.getEst())));
    taskRepository.save(task);

    return String.format("Assigned task to %s successfully", task.getAssignee());
  }

  @Override
  public String addLogwork(Timestamp timeBegin, Timestamp timeEnd, Integer taskId) {
    Optional<Task> t = taskRepository.findById(taskId);
    if (t.isEmpty()) {
      return "Not found task to add logwork";
    }
    Task task = t.get();
    if (task.getAssignee().equals(UNDEFINED)) {
      return "Must assign task to someone before adding logwork";
    }
    if (timeBegin.compareTo(timeEnd) >= 0) {
      return "Invalid timeline";
    }
    Logwork logwork = new Logwork(timeBegin, timeEnd);
    logwork.setTask(task);
    logworkRepository.save(logwork);
    return "Add logwork successfully";
  }

  public LocalDate calculateDeadline(Integer est) {
    Integer deadlineHour = LocalDateTime.now().getHour();
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
      System.out.println(lw.getTimeEnd());
      System.out.println(lw.getTimeStart());
      System.out.println(lw.getTimeEnd().getTime() - lw.getTimeStart().getTime());
      remainingMS = remainingMS - (lw.getTimeEnd().getTime() - lw.getTimeStart().getTime());
    }
    if (remainingMS < 0L) {
      return "This task is done";
    }
    String result = "Remaining: ";
    long hours = (long) Math.ceil(TimeUnit.MILLISECONDS.toHours(remainingMS));
    result = result.concat(String.format("%s hours ", hours));

    return result;
  }
}


