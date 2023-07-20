package com.example.mytask.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataResponse<T> {

  private String message;
  private T data;

  public DataResponse(String message, T data) {
    this.message = message;
    this.data = data;
  }

  public DataResponse(String message) {
    this.message = message;
  }
}
