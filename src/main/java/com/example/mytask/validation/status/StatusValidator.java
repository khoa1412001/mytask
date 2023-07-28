package com.example.mytask.validation.status;


import com.example.mytask.constant.TaskStatus;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class StatusValidator implements ConstraintValidator<StatusValidation, String> {

  @Override
  public void initialize(StatusValidation constraintAnnotation) {
  }

  @Override
  public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
    for (TaskStatus status : TaskStatus.values()) {
      if (status.name().equals(s)) {
        return true;
      }
    }
    return false;
  }
}
