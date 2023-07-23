package com.example.mytask.payload;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class DataResponse<T> {

  private int status;
  private String message;
  private T data;

  public DataResponse(int status, String message, T data) {
    this.status = status;
    this.message = message;
    this.data = data;
  }

  public DataResponse(int status, String message) {
    this.status = status;
    this.message = message;
  }

  public DataResponse(String message) {
    this.message = message;
  }
}
