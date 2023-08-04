package com.example.mytask.dto;

import com.example.mytask.validation.point.PointValidation;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskDTO {

  @NotBlank(message = "Title must not be empty")
  private String title;
  @PointValidation
  private Integer point;
}
