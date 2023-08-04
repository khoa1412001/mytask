package com.example.mytask.controller;


import com.example.mytask.config.IntegrationGateway;
import com.example.mytask.dto.UserDTO;
import com.example.mytask.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.example.mytask.constant.ServiceRoutePath.GET_USER_CHANNEL;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class UserControllerTests {

  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private IntegrationGateway integrationGateway;

  @Autowired
  ObjectMapper objectMapper;

  User USER_RECORD1 = new User(1, "Khoa", "01/14/2000", "khoa@gmail.com", "1234567891", "TV",
      "Member");
  User USER_RECORD2 = new User(2, "An", "01/01/2002", "An@gmail.com", "1234567890", "TV",
      "Scrum master");


  @Test
  public void givenUserId_whenGetUserById_thenReturnUser() throws Exception {
    int userId = 1;
    ResponseEntity responseEntity = new ResponseEntity<>(USER_RECORD1, HttpStatus.OK);
    given(integrationGateway.process(userId, GET_USER_CHANNEL)).willReturn(responseEntity);

    ResultActions response = mockMvc.perform(get("/api/v1/user/{id}", userId))
        .andExpect(status().isOk())
        .andDo(print())
        .andExpect(jsonPath("$.dob", is(USER_RECORD1.getDob())))
        .andExpect(jsonPath("$.email", is(USER_RECORD1.getEmail())))
        .andExpect(jsonPath("$.name", is(USER_RECORD1.getName())))
        .andExpect(jsonPath("$.office", is(USER_RECORD1.getOffice())))
        .andExpect(jsonPath("$.role", is(USER_RECORD1.getRole())))
        .andExpect(jsonPath("$.phone", is(USER_RECORD1.getPhone())));
  }

  @Test
  public void givenInvalidUserId_WhenGetUserById_thenStatus400() throws Exception {
    String userId = "a";
    ResultActions response = mockMvc.perform(get("/api/v1/user/{id}", userId))
        .andExpect(status().is4xxClientError())
        .andDo(print());
  }

  @Test
  public void givenInvalidUser_WhenCreateUser_thenStatus400() throws Exception {
    UserDTO user = new UserDTO("Khoa", "", "khoa@mail", "0901231", "TV", "S");
    String requestBody = objectMapper.writeValueAsString(user);
    mockMvc.perform(post("/api/v1/user").contentType("application/json")
        .content(requestBody)).andDo(print()).andExpect(status().is4xxClientError());
  }

  @Test
  public void givenUser_WhenCreateUser_thenStatus200() throws Exception {
    UserDTO user = new UserDTO("Khoa", "01/14/2001", "khoa@gmail.com", "0901231123", "TV",
        "Member");
    String requestBody = objectMapper.writeValueAsString(user);
    mockMvc.perform(post("/api/v1/user").contentType("application/json")
        .content(requestBody)).andDo(print()).andExpect(status().isOk());
  }
}
