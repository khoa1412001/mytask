package com.example.mytask.controller;


import com.example.mytask.config.IntegrationGateway;
import com.example.mytask.model.User;
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

import static com.example.mytask.constant.ServiceRoutePath.GET_USER_CHANNEL;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class UserControllerTests {

  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private IntegrationGateway integrationGateway;
//  @Autowired
//  private IntegrationConfig integrationConfig;
//  @MockBean
//  private UserRepository userRepository;

//  @BeforeEach
//  public void setup() throws Exception {
//    this.mockMvc = MockMvcBuilders.webAppContextSetup()
//  }

  @Test
  public void givenUserId_whenGetUserById_thenReturnUser() throws Exception {
    int userId = 1;
    User user = User.builder()
        .id(1)
        .dob("01/14/2001")
        .email("khoa@gmail.com")
        .name("Khoa")
        .office("TV")
        .role("Other")
        .phone("0909511941")
        .build();
    ResponseEntity responseEntity = new ResponseEntity<>(user, HttpStatus.OK);
    given(integrationGateway.process(userId, GET_USER_CHANNEL)).willReturn(responseEntity);

    ResultActions response = mockMvc.perform(get("/api/v1/user/{id}", userId))
//    System.out.println(response.toString());
//    response.andExpect((status().isOk()))
////        .andReturn().getResponse().getContentAsString();
        .andDo(print())
//        .objectMapper
//        .andExpect(jsonPath("$.id"), is(user.getId()))
        .andExpect(jsonPath("$.dob", is(user.getDob())))
        .andExpect(jsonPath("$.email", is(user.getEmail())))
        .andExpect(jsonPath("$.name", is(user.getName())))
        .andExpect(jsonPath("$.office", is(user.getOffice())))
        .andExpect(jsonPath("$.role", is(user.getRole())))
        .andExpect(jsonPath("$.phone", is(user.getPhone())));

  }
}
