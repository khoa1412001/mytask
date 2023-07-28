package com.example.mytask.dto;

import com.example.mytask.validation.point.PointValidation;
import com.example.mytask.validation.status.StatusValidation;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskUpdateDTO {

  @NotNull(message = "Title is required")
  private String title;
  @PointValidation
  private Integer point;
  @StatusValidation
  private String status;
}
