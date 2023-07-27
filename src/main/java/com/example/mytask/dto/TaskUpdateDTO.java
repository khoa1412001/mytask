package com.example.mytask.dto;

import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskUpdateDTO {

  @NotNull
  private String title;
  private Integer point;
  private String status;
}
