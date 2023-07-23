package com.example.mytask.exception;

import com.example.mytask.payload.DataResponse;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ValidExceptionHandle {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public DataResponse handleValidationErrors(MethodArgumentNotValidException ex) {
    List list = ex.getBindingResult().getAllErrors().stream()
        .map(fieldError -> fieldError.getDefaultMessage())
        .collect(Collectors.toList());

    return new DataResponse(400, "Invalid input", list);
  }
}
