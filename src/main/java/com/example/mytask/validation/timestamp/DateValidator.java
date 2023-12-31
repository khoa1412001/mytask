package com.example.mytask.validation.timestamp;

import java.sql.Timestamp;
import java.time.Instant;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DateValidator implements
    ConstraintValidator<DateValidation, String> {


  @Override
  public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
    try {
      Timestamp.from(Instant.ofEpochMilli(Long.valueOf(s)));
      return true;
    } catch (Exception e) {
      return false;
    }
  }
}
