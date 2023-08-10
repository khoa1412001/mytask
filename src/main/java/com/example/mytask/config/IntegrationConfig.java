package com.example.mytask.config;

import static com.example.mytask.constant.RoutePath.*;
import static com.example.mytask.constant.ServiceRoutePath.*;

import com.example.mytask.model.Logwork;
import com.example.mytask.model.Task;
import com.example.mytask.model.User;
import com.example.mytask.service.TaskService;
import com.example.mytask.service.UserService;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.handler.ServiceActivatingHandler;
import org.springframework.integration.router.HeaderValueRouter;

@EnableIntegration
@Configuration
public class IntegrationConfig {


  @Autowired
  private UserService userService;

  @Autowired
  private TaskService taskService;

  @Bean(name = RESULT_CHANNEL)
  public DirectChannel resultChannel() {
    return MessageChannels.direct(RESULT_CHANNEL).get();
  }

  @Bean(name = INPUT_CHANNEL)
  public DirectChannel inputChannel() {
    return MessageChannels.direct(INPUT_CHANNEL).get();
  }


  @Bean
  public IntegrationFlow myFlow() {
    HandleLogRequest handleLogRequest = new HandleLogRequest();
    return IntegrationFlows.from(inputChannel())
        .handle(new ServiceActivatingHandler(handleLogRequest))
        .route(router()).get();
  }

  public HeaderValueRouter router() {
    return new HeaderValueRouter("action");
  }

  @ServiceActivator(inputChannel = GET_USERS_CHANNEL)
  public ResponseEntity<Object> getUsers() {
    return ResponseEntity.ok(userService.getUsers());
  }

  @ServiceActivator(inputChannel = GET_USER_CHANNEL)
  public ResponseEntity<Object> getUser(int userId) {
    User user = userService.getUser(userId);
    return ResponseEntity.ok(user);
  }

  @ServiceActivator(inputChannel = CREATE_USER_CHANNEL)
  public ResponseEntity<Object> createUser(User user) {
    User result = userService.createUser(user);
    return ResponseEntity.ok(result);
  }

  @ServiceActivator(inputChannel = EDIT_USER_CHANNEL)
  public ResponseEntity<Object> editUser(User user) {
    User result = userService.editUser(user);
    return ResponseEntity.ok(result);
  }

  @ServiceActivator(inputChannel = GET_TASKS_CHANNEL)
  public ResponseEntity<Object> getTasks() {
    return ResponseEntity.ok(taskService.getTasks());
  }

  @ServiceActivator(inputChannel = GET_TASK_CHANNEL)
  public ResponseEntity<Object> getTask(int taskId) {
    return ResponseEntity.ok(taskService.getTask(taskId));
  }

  @ServiceActivator(inputChannel = CREATE_TASK_CHANNEL)
  public ResponseEntity<Object> createTask(Task task) {
    return ResponseEntity.ok(taskService.createTask(task));
  }


  @ServiceActivator(inputChannel = EDIT_TASK_CHANNEL)
  public ResponseEntity<Object> editTask(Task task) {
    Task result = taskService.editTask(task);
    return ResponseEntity.ok(result);
  }

  @ServiceActivator(inputChannel = ASSIGN_TASK_CHANNEL)
  public ResponseEntity<Object> assignTask(Map<String, Integer> map) {
    return ResponseEntity.ok(taskService.assignTask(map.get("userId"), map.get("taskId")));
  }

  @ServiceActivator(inputChannel = LOG_WORK_CHANNEL)
  public ResponseEntity<Object> addLogwork(Map<String, String> map) {
    Logwork result = taskService.addLogwork(
        Timestamp.from(Instant.ofEpochMilli(Long.valueOf(map.get("timeBegin")))),
        Timestamp.from(Instant.ofEpochMilli(Long.valueOf(map.get("timeEnd")))),
        Integer.parseInt(map.get("taskId")));
    return ResponseEntity.ok(result);
  }
}
