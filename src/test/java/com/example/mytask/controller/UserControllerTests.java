package com.example.mytask.controller;


import com.example.mytask.config.IntegrationGateway;
import com.example.mytask.dto.UserDTO;
import com.example.mytask.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.example.mytask.constant.ServiceRoutePath.EDIT_USER_CHANNEL;
import static com.example.mytask.constant.ServiceRoutePath.GET_USERS_CHANNEL;
import static com.example.mytask.constant.ServiceRoutePath.GET_USER_CHANNEL;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class UserControllerTests {

  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private IntegrationGateway integrationGateway;

  @Autowired
  ObjectMapper objectMapper;

  private static final String END_POINT_PATH = "/api/v1/user";

  User userRecord1;
  User userRecord2;

  @BeforeEach
  void init() {
    userRecord1 = new User(1, "Khoa", "01/14/2000", "khoa@gmail.com", "1234567891", "TV",
        "Member");
    userRecord2 = new User(2, "An", "01/01/2002", "An@gmail.com", "1234567890", "TV",
        "Scrum master");
  }

  @Test
  void testGetUserWithNoError() throws Exception {
    int userId = 1;
    ResponseEntity responseEntity = new ResponseEntity<>(userRecord1, HttpStatus.OK);
    given(integrationGateway.process(userId, GET_USER_CHANNEL)).willReturn(responseEntity);

    ResultActions response = mockMvc.perform(get(END_POINT_PATH + "/{id}", userId))
        .andExpect(status().isOk())
        .andDo(print())
        .andExpect(jsonPath("$.dob", is(userRecord1.getDob())))
        .andExpect(jsonPath("$.email", is(userRecord1.getEmail())))
        .andExpect(jsonPath("$.name", is(userRecord1.getName())))
        .andExpect(jsonPath("$.office", is(userRecord1.getOffice())))
        .andExpect(jsonPath("$.role", is(userRecord1.getRole())))
        .andExpect(jsonPath("$.phone", is(userRecord1.getPhone())));
  }

  @Test
  void testGetUserWithError() throws Exception {
    String userId = "a";
    ResultActions response = mockMvc.perform(get(END_POINT_PATH + "/{id}", userId))
        .andExpect(status().is4xxClientError())
        .andDo(print());
  }

  @Test
  void testCreateUserWithError() throws Exception {
    UserDTO user = new UserDTO("Khoa", "", "khoa@mail", "0901231", "TV", "S");
    String requestBody = objectMapper.writeValueAsString(user);
    mockMvc.perform(post("/api/v1/user").contentType("application/json")
        .content(requestBody)).andDo(print()).andExpect(status().is4xxClientError());
  }

  @Test
  void testCreateUserWithNoError() throws Exception {
    UserDTO user = new UserDTO("Khoa", "01/14/2001", "khoa@gmail.com", "0901231123", "TV",
        "Member");
    String requestBody = objectMapper.writeValueAsString(user);
    mockMvc.perform(post(END_POINT_PATH).contentType("application/json")
        .content(requestBody)).andDo(print()).andExpect(status().isOk());
  }

  @Test
  void testGetUsersWithNoError() throws Exception {
    List<User> userList = new ArrayList<>();
    userList.add(userRecord1);
    userList.add(userRecord2);
    given(integrationGateway.process(GET_USERS_CHANNEL)).willReturn(ResponseEntity.ok(userList));
    mockMvc.perform(get(END_POINT_PATH)).andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(2)));
  }

  @Test
  void testEditUserWithError() throws Exception {
    User updateUser = User.builder().name("Khoatest").email("khoa@gmail.com").dob("11/11/2011")
        .phone("1234567")
        .build();
    mockMvc.perform(put(END_POINT_PATH + "/{id}", 1)
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(updateUser)))
        .andDo(print()).andExpect(status().is4xxClientError());
  }

  @Test
  void testEditUserWithNoError() throws Exception {
    UserDTO updateUser = new UserDTO("Khoa", "01/14/2001", "khoa@gmail.com", "0901231123", "TV",
        "Member");

    given(integrationGateway.process(any(User.class), eq(EDIT_USER_CHANNEL))).willReturn(
        ResponseEntity.ok(userRecord1));
    mockMvc.perform(put(END_POINT_PATH + "/{id}", 1)
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(updateUser)))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value((userRecord1.getName())))
        .andExpect(jsonPath("$.id").value(1));
  }
}
