package com.example.mytask.controller;

import static com.example.mytask.constant.ServiceRoutePath.ASSIGN_TASK_CHANNEL;
import static com.example.mytask.constant.ServiceRoutePath.CREATE_TASK_CHANNEL;
import static com.example.mytask.constant.ServiceRoutePath.EDIT_TASK_CHANNEL;
import static com.example.mytask.constant.ServiceRoutePath.GET_TASKS_CHANNEL;
import static com.example.mytask.constant.ServiceRoutePath.GET_TASK_CHANNEL;
import static com.example.mytask.constant.ServiceRoutePath.LOG_WORK_CHANNEL;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.example.mytask.config.IntegrationGateway;
import com.example.mytask.constant.CONSTANT;
import com.example.mytask.dto.TaskUpdateDTO;
import com.example.mytask.model.Logwork;
import com.example.mytask.model.Task;
import com.example.mytask.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@WebMvcTest(TaskController.class)
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class TaskControllerTests {

  private final static String END_POINT_PATH = "/api/v1/task";
  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private IntegrationGateway integrationGateway;

  @Autowired
  ObjectMapper objectMapper;

  Task taskRecord1;
  Task taskRecord2;

  @BeforeEach
  void init() {
    taskRecord1 = Task.builder().id(1).title("Test task").est(1).point(1).build();
    taskRecord2 = Task.builder().id(2).title("Task controller").est(10).point(8).build();
  }

  @Test
  void testGetTasksWithNoError() throws Exception {
    List<Task> taskList = new ArrayList<>();
    taskList.add(taskRecord1);
    taskList.add(taskRecord2);
    given(integrationGateway.process(GET_TASKS_CHANNEL)).willReturn(ResponseEntity.ok(taskList));
    mockMvc.perform(get(END_POINT_PATH)).andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$", hasSize(2)));
  }

  @Test
  void testGetTaskWithNoError() throws Exception {
    given(integrationGateway.process(1, GET_TASK_CHANNEL)).willReturn(
        ResponseEntity.ok(taskRecord1));
    mockMvc.perform(get(END_POINT_PATH + "/{id}", 1))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$.id").value(1));
  }

  @Test
  void testGetTaskWithError() throws Exception {
    mockMvc.perform(get(END_POINT_PATH + "/{id}", "a"))
        .andDo(print())
        .andExpect(status().is4xxClientError());
  }

  @Test
  void testCreateTaskWithNoError() throws Exception {
    given(integrationGateway.process(any(Task.class), eq(CREATE_TASK_CHANNEL))).willReturn(
        ResponseEntity.ok(taskRecord1));
    mockMvc.perform(post(END_POINT_PATH).contentType("application/json")
            .content(objectMapper.writeValueAsString(taskRecord1)))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$.id").value(1));
  }

  @Test
  void testCreateTaskWithError() throws Exception {
    Task task = Task.builder().title("Test error task").build();
    mockMvc.perform(post(END_POINT_PATH).contentType("application/json")
            .content(objectMapper.writeValueAsString(task)))
        .andDo(print())
        .andExpect(status().is4xxClientError());
  }

  @Test
  void testEditTaskWithNoError() throws Exception {
    TaskUpdateDTO taskUpdateDTO = TaskUpdateDTO.builder().title("test edit").point(2).status(
        CONSTANT.STATUS_LIST.get(2)).build();
    given(integrationGateway.process(any(Task.class), eq(EDIT_TASK_CHANNEL)))
        .willReturn(ResponseEntity.ok(taskRecord1));
    mockMvc.perform(put(END_POINT_PATH + "/{id}", 1).contentType("application/json")
            .content(objectMapper.writeValueAsString(taskUpdateDTO))).andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(taskRecord1.getId()));
  }

  @Test
  void testEditTaskWithError() throws Exception {
    TaskUpdateDTO taskUpdateDTO = TaskUpdateDTO.builder().title("test edit error")
        .status("in progress").point(2).build();
    ;
    mockMvc.perform(put(END_POINT_PATH + "/{id}", 1)
            .contentType("application/json").content(objectMapper.writeValueAsString(taskUpdateDTO)))
        .andDo(print())
        .andExpect(status().is4xxClientError())
        .andExpect(jsonPath("$.errors[0]").value("status: Invalid status"));
  }

  @Test
  void testAssignTaskWithNoError() throws Exception {
    User user = User.builder().id(1).name("Khoa").build();
    taskRecord2.setAssignee(user);
    given(integrationGateway.process(any(Map.class), eq(ASSIGN_TASK_CHANNEL))).willReturn(
        ResponseEntity.ok(taskRecord2));
    mockMvc.perform(post(END_POINT_PATH + "/{taskId}/user/{userId}", 2, 1))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(2));
  }

  @Test
  void testAssignTaskWithError() throws Exception {
    mockMvc.perform(post(END_POINT_PATH + "/{taskId}/user/{userId}", "a", 1))
        .andDo(print())
        .andExpect(status().is4xxClientError());
  }

  @Test
  void testAddLogworkWithNoError() throws Exception {
    Timestamp timeBegin = Timestamp.valueOf(LocalDateTime.of(2021, 1, 1, 10, 20));
    Timestamp timeEnd = Timestamp.valueOf(LocalDateTime.of(2021, 1, 1, 10, 40));
    Logwork logwork = new Logwork(timeBegin, timeEnd);
    given(integrationGateway.process(any(Map.class), eq(LOG_WORK_CHANNEL))).willReturn(
        ResponseEntity.ok(logwork));
    mockMvc.perform(post(END_POINT_PATH + "/{id}/logwork", 1)
            .param("timeBegin", String.valueOf(timeBegin.getTime()))
            .param("timeEnd", String.valueOf(timeEnd.getTime())))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  void testAddLogworkWithError() throws Exception {
    mockMvc.perform(post(END_POINT_PATH + "/{id}/logwork", 1)
            .param("timeBegin", "15:20:21")
            .param("timeEnd", "17:20:21"))
        .andDo(print())
        .andExpect(status().is4xxClientError());
  }
}
