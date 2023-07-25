package com.example.mytask.model.dto;

import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssignDTO {

  @NotNull(message = "User ID is required")
  private int userId;
  @NotNull(message = "Task ID is required")
  private int taskId;
}
