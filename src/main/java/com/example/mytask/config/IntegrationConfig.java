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
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.annotation.Router;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.handler.ServiceActivatingHandler;
import org.springframework.integration.router.HeaderValueRouter;
import org.springframework.messaging.MessageHandlingException;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.handler.annotation.Header;

@EnableIntegration
@Configuration
public class IntegrationConfig {

  private static Logger logger = LogManager.getLogger();
  @Autowired
  private HandleLogRequest handleLogRequest;
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

  @Bean(name = ROUTE_CHANNEL)
  public DirectChannel routerChannel() {
    return MessageChannels.direct(ROUTE_CHANNEL).get();
  }

  @Bean
  public IntegrationFlow myFlow() {
    return IntegrationFlows.from(inputChannel())
        .handle(new ServiceActivatingHandler(handleLogRequest))
        .route(router()).get();
  }

  public HeaderValueRouter router() {
    HeaderValueRouter router = new HeaderValueRouter("action");
    return router;
  }

  @ServiceActivator(inputChannel = ERROR_CHANNEL)
//  MessageHandlingException
  public ResponseEntity errorChannel(Exception payload) {
    payload.printStackTrace();
    logger.error(payload.getMessage());
    return new ResponseEntity(
        "An error occurred, please try again later", HttpStatus.BAD_REQUEST);
  }

  @ServiceActivator(inputChannel = GET_USERS_CHANNEL)
  public ResponseEntity getUsers() {
    return ResponseEntity.ok(userService.getUsers());
  }

  @ServiceActivator(inputChannel = GET_USER_CHANNEL)
  public ResponseEntity getUser(Integer userId) {
    Optional<User> user = userService.getUser(userId);
    return user.isEmpty() ?
        ResponseEntity.ok("User not found") :
        ResponseEntity.ok(user.get());
  }

  @ServiceActivator(inputChannel = CREATE_USER_CHANNEL)
  public ResponseEntity createUser(User user) {
    String result = userService.createUser(user);
    return ResponseEntity.ok(result);
  }

  @ServiceActivator(inputChannel = EDIT_USER_CHANNEL)
  public ResponseEntity editUser(User user) {
    String result = userService.editUser(user);
    return ResponseEntity.ok(result);
  }

  @ServiceActivator(inputChannel = GET_TASKS_CHANNEL)
  public ResponseEntity getTasks() {
    return ResponseEntity.ok(taskService.getTasks());
  }

  @ServiceActivator(inputChannel = GET_TASK_CHANNEL)
  public ResponseEntity getTask(Integer taskId) {
    Optional<Task> task = taskService.getTask(taskId);
    return ResponseEntity.ok(task.isEmpty() ? "Task not found" : task.get());
  }

  @ServiceActivator(inputChannel = CREATE_TASK_CHANNEL)
  public ResponseEntity createTask(Task task) {
    return ResponseEntity.ok(taskService.createTask(task));
  }


  @ServiceActivator(inputChannel = EDIT_TASK_CHANNEL)
  public ResponseEntity editTask(Task task) {
    String result = taskService.editTask(task);
    return ResponseEntity.ok(result);
  }

  @ServiceActivator(inputChannel = ASSIGN_TASK_CHANNEL)
  public ResponseEntity assignTask(Map<String, Integer> map) {
    String result = taskService.assignTask(map.get("userId"), map.get("taskId"));
    return ResponseEntity.ok(result);
  }

  @ServiceActivator(inputChannel = LOG_WORK_CHANNEL)
  public ResponseEntity addLogwork(Map<String, String> map) {
    String result = taskService.addLogwork(
        Timestamp.from(Instant.ofEpochMilli(Long.valueOf(map.get("timeBegin")))),
        Timestamp.from(Instant.ofEpochMilli(Long.valueOf(map.get("timeEnd")))),
        Integer.valueOf(map.get("taskId")));
    return ResponseEntity.ok(result);
  }
}
