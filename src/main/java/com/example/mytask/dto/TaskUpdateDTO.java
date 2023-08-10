package com.example.mytask.dto;

import com.example.mytask.validation.point.PointValidation;
import com.example.mytask.validation.status.StatusValidation;
import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TaskUpdateDTO {

  @NotBlank(message = "Title is required")
  private String title;
  @PointValidation
  private Integer point;
  @StatusValidation
  private String status;
}
