package com.example.mytask.service;


import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;


import com.example.mytask.exceptions.UserNotFoundException;
import com.example.mytask.model.User;
import com.example.mytask.repository.UserRepository;
import com.example.mytask.service.impl.UserServiceImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTests {

  @Mock
  UserRepository userRepository;

  @InjectMocks
  UserServiceImpl userService;
  User USER_RECORD1 = new User(1, "Khoa", "01/14/2000", "khoa@gmail.com", "1234567891", "TV",
      "Member");
  User USER_RECORD2 = new User(2, "An", "01/01/2002", "An@gmail.com", "1234567890", "TV",
      "Scrum master");

  @Test
  void testGetUsersWithNoError() throws Exception {
    List<User> mockUser = new ArrayList<>();
    mockUser.add(USER_RECORD1);
    mockUser.add(USER_RECORD2);
    given(userRepository.findAll()).willReturn(mockUser);
    List<User> userList = userService.getUsers();
    assertThat(userList)
        .isNotNull()
        .hasSameSizeAs(mockUser);
    verify(userRepository).findAll();
  }

  @Test
  void testGetUserWithNoError() throws Exception {
    given(userRepository.findById(USER_RECORD1.getId())).willReturn(
        Optional.ofNullable(USER_RECORD1));

    User user = userService.getUser(USER_RECORD1.getId());

    assertThat(user).isNotNull();
    assertThat(user.getId()).isEqualTo(USER_RECORD1.getId());

    verify(userRepository).findById(USER_RECORD1.getId());
  }

  @Test
  void testUserWithError() throws Exception {
    int userId = 3;
    given(userRepository.findById(any(Integer.class))).willReturn(Optional.ofNullable(null));

    UserNotFoundException aThrows = assertThrows(UserNotFoundException.class,
        () -> userService.getUser(userId));
    assertThat(aThrows.getMessage()).isEqualTo("User could not be found");

    verify(userRepository).findById(any(int.class));
  }

  @Test
  void testCreateUserWithNoError() throws Exception {
    User user = User.builder()
        .name("Khoa")
        .role("Member")
        .phone("0909090909")
        .dob("12/12/1992")
        .email("Khoa@gmail.com")
        .office("TV")
        .build();
    given(userRepository.save(any(User.class))).willReturn(user);

    User savedUser = userService.createUser(user);

    assertThat(savedUser.getName()).isEqualTo(user.getName());

    verify(userRepository).save(any(User.class));
  }

  @Test
  void testEditUserWithNoError() throws Exception {
    User user = User.builder()
        .id(1)
        .name("Khoa")
        .role("Member")
        .phone("0909090909")
        .dob("12/12/1992")
        .email("Khoa@gmail.com")
        .office("TV")
        .build();
    given(userRepository.existsById(1)).willReturn(true);
    given(userRepository.save(user)).willReturn(user);

    User savedUser = userService.editUser(user);

    assertThat(savedUser.getDob()).isEqualTo(user.getDob());
  }

  @Test
  void testEditUserWithErrors() throws Exception {
    given(userRepository.existsById(any(Integer.class))).willReturn(false);

    UserNotFoundException ex = assertThrows(UserNotFoundException.class,
        () -> userService.editUser(USER_RECORD1));

    assertThat(ex.getMessage()).isEqualTo("User could not be found");
    verify(userRepository).existsById(any(Integer.class));
  }

}
