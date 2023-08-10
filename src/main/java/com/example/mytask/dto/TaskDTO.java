package com.example.mytask.dto;

import com.example.mytask.validation.point.PointValidation;
import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder(toBuilder = true)
public class TaskDTO {

  @NotBlank(message = "Title must not be empty")
  private String title;
  @PointValidation
  private Integer point;
}
