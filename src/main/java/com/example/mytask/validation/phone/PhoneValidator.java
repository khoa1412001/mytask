package com.example.mytask.validation.phone;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneValidator implements ConstraintValidator<PhoneValidation, String> {


  @Override
  public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
    return s != null && s.matches("\\d+")
        && (s.length() == 10);
  }
}
