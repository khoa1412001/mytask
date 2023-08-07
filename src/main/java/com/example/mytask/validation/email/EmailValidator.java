package com.example.mytask.validation.email;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<EmailValidation, String> {

  @Override
  public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
    return (s != null) && s.matches("^[\\w.+\\-]+@gmail\\.com$");
  }
}
