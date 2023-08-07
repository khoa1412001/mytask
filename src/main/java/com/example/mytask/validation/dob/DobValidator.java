package com.example.mytask.validation.dob;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DobValidator implements ConstraintValidator<DobValidation, String> {

  @Override
  public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
    if (s == null) {
      return false;
    }
    SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
    format.setLenient(false);
    try {
      format.parse(s);
      return true;
    } catch (ParseException ex) {
      return false;
    }
  }
}
