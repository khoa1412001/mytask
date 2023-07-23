package com.example.mytask.validation.email;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<EmailValidation, String> {

  @Override
  public void initialize(EmailValidation constraintAnnotation) {
  }

  @Override
  public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
//    if (s==null) return false;
    return (s != null) && s.matches("^[\\w.+\\-]+@gmail\\.com$");
  }
}
