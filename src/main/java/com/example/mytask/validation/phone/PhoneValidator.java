package com.example.mytask.validation.phone;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneValidator implements ConstraintValidator<PhoneValidation, String> {

  @Override
  public void initialize(PhoneValidation phoneNumber) {
  }

  @Override
  public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
    return s != null && s.matches("[0-9]+")
        && (s.length() == 10);
  }
}
