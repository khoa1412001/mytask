package com.example.mytask.validation.status;


import static com.example.mytask.constant.CONSTANT.STATUS_LIST;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class StatusValidator implements ConstraintValidator<StatusValidation, String> {


  @Override
  public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
    return STATUS_LIST.contains(s);
  }
}
