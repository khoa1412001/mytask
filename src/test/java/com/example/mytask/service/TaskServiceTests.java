package com.example.mytask.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.example.mytask.exceptions.InvalidTimelineException;
import com.example.mytask.exceptions.TaskNotFoundException;
import com.example.mytask.exceptions.UserNotFoundException;
import com.example.mytask.model.Logwork;
import com.example.mytask.model.Task;
import com.example.mytask.model.User;
import com.example.mytask.repository.LogworkRepository;
import com.example.mytask.repository.TaskRepository;
import com.example.mytask.repository.UserRepository;
import com.example.mytask.service.impl.TaskServiceImpl;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TaskServiceTests {

  @Mock
  TaskRepository taskRepository;
  @Mock
  UserRepository userRepository;
  @Mock
  LogworkRepository logworkRepository;
  @InjectMocks
  TaskServiceImpl taskService;
  Task taskRecord1;
  Task taskRecord2;
  User userRecord;

  @BeforeEach()
  void init() {
    userRecord = User.builder().name("Khoa").build();
    taskRecord1 = Task.builder().id(1).assignee(userRecord).title("Task1").est(2)
        .logwork(List.of()).build();
    taskRecord2 = Task.builder().id(2).assignee(userRecord).title("Task2").build();
  }

  @Test
  void testGetTasksWithNoError() throws Exception {
    List<Task> mockTask = new ArrayList<>();
    mockTask.add(taskRecord1);
    mockTask.add(taskRecord2);
    given(taskRepository.findAll()).willReturn(mockTask);
    List<Task> taskList = taskService.getTasks();
    assertThat(taskList)
        .isNotNull()
        .hasSameSizeAs(mockTask);
    verify(taskRepository).findAll();
  }

  @Test
  void testGetTaskWithNoError() throws Exception {

    given(taskRepository.findById(taskRecord1.getId())).willReturn(
        Optional.ofNullable(taskRecord1));

    Task task = taskService.getTask(taskRecord1.getId());

    assertThat(task).isNotNull();
    assertThat(task.getId()).isEqualTo(taskRecord1.getId());
    assertThat(task.getRemaining()).isEqualTo("Remaining: 2 hours ");

    verify(taskRepository).findById(taskRecord1.getId());
  }

  @Test
  void testGetTaskWithErrors() throws Exception {
    int taskId = 3;
    given(taskRepository.findById(any(Integer.class))).willReturn(Optional.ofNullable(null));

    TaskNotFoundException aThrows = assertThrows(TaskNotFoundException.class,
        () -> taskService.getTask(taskId));
    assertThat(aThrows.getMessage()).isEqualTo("Task could not be found");

    verify(taskRepository).findById(any(int.class));
  }

  @Test
  void testCreateTastWithNoError() throws Exception {

    Task task = Task.builder().point(2).title("tasktest").build();
    given(taskRepository.save(any(Task.class))).willReturn(task);
    given(userRepository.findById((any(Integer.class)))).willReturn(
        Optional.ofNullable(userRecord));

    Task savedTask = taskService.createTask(task);

    assertThat(savedTask.getTitle()).isEqualTo(task.getTitle());

    verify(taskRepository).save(any(Task.class));
  }

  @Test
  void testEditTaskWithNoError() throws Exception {
    Task updateTask = Task.builder().id(1).title("update task test").build();
    given(taskRepository.existsById(1)).willReturn(true);
    given(taskRepository.save(any(Task.class))).willReturn(updateTask);

    Task updatedTask = taskService.editTask(updateTask);

    assertThat(updatedTask.getTitle()).isEqualTo(updateTask.getTitle());

    verify(taskRepository).existsById(any(Integer.class));
    verify(taskRepository).save(any(Task.class));
  }

  @Test
  void testEditTaskwithError() throws Exception {
    given(taskRepository.existsById(any(Integer.class))).willReturn(false);
    assertThrows(TaskNotFoundException.class, () -> taskService.editTask(taskRecord1));
    verify(taskRepository).existsById(any(Integer.class));
  }

  @Test
  void testAssignTaskWithNoError() throws Exception {
    given(taskRepository.findById(1)).willReturn(Optional.ofNullable(taskRecord1));
    given(userRepository.findById(1)).willReturn(Optional.ofNullable(userRecord));
    given(taskRepository.save(any(Task.class))).willReturn(taskRecord1);

    Task savedTask = taskService.assignTask(1, 1);

    assertThat(savedTask).isNotNull();

    assertThat(savedTask.getDeadline()).isEqualTo(Date.valueOf(LocalDate.now()));
    assertThat(savedTask.getAssignee()).isEqualTo(userRecord.getName());

    verify(taskRepository).findById(1);
    verify(userRepository).findById(1);
  }

  @Test
  void testAssignTaskWithUserError() throws Exception {
    given(userRepository.findById(any(Integer.class))).willReturn(Optional.ofNullable(null));

    assertThrows(UserNotFoundException.class, () -> taskService.assignTask(1, 1));

    verify(userRepository).findById(any(Integer.class));

  }

  @Test
  void testAssignTaskWithTaskError() throws Exception {
    given(userRepository.findById(any(Integer.class))).willReturn(Optional.ofNullable(userRecord));
    given(taskRepository.findById(any(Integer.class))).willReturn(Optional.ofNullable(null));

    assertThrows(TaskNotFoundException.class, () -> taskService.assignTask(1, 1));

    verify(taskRepository).findById(any(Integer.class));
  }

  @Test
  void testAddLogworkWithNoError() throws Exception {
    Timestamp timeBegin = Timestamp.valueOf(LocalDateTime.of(2021, 1, 1, 10, 20));
    Timestamp timeEnd = Timestamp.valueOf(LocalDateTime.of(2021, 1, 1, 10, 40));
    Logwork logwork = new Logwork(timeBegin, timeEnd);

    given(taskRepository.findById(any(Integer.class))).willReturn(Optional.ofNullable(taskRecord1));
    given(logworkRepository.save((any(Logwork.class)))).willReturn(logwork);

    Logwork savedLogwork = taskService.addLogwork(timeBegin, timeEnd, 1);

    assertThat(savedLogwork).isNotNull();
    assertThat(savedLogwork.getTask()).isEqualTo(taskRecord1);

    verify(taskRepository).findById(any(Integer.class));
    verify(logworkRepository).save(any(Logwork.class));
  }

  @Test
  void testAddLogworkWithTaskError() throws Exception {
    Timestamp timeBegin = Timestamp.valueOf(LocalDateTime.of(2021, 1, 1, 10, 20));
    Timestamp timeEnd = Timestamp.valueOf(LocalDateTime.of(2021, 1, 1, 10, 40));

    given(taskRepository.findById(any(Integer.class))).willReturn(Optional.ofNullable(null));

    assertThrows(TaskNotFoundException.class, () -> taskService.addLogwork(timeBegin, timeEnd, 1));

  }

  @Test
  void testAddLogworkWithTimelineError() throws Exception {
    Timestamp timeBegin = Timestamp.valueOf(LocalDateTime.of(2021, 1, 1, 10, 20));
    Timestamp timeEnd = Timestamp.valueOf(LocalDateTime.of(2021, 1, 1, 10, 40));

    given(taskRepository.findById(any(Integer.class))).willReturn(Optional.ofNullable(taskRecord1));

    assertThrows(InvalidTimelineException.class,
        () -> taskService.addLogwork(timeEnd, timeBegin, 1));

  }
}
