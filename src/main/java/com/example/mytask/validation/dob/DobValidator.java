package com.example.mytask.validation.dob;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DobValidator implements ConstraintValidator<DobValidation, String> {

  @Override
  public void initialize(DobValidation constraintAnnotation) {
  }

  @Override
  public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
//    return s != null && s.matches("^(0[1-9]|1[012])/(0[1-9]|[12][0-9]|[3][01])/\\\\d{4}$");
    System.out.println(s);
    if (s == null) {
      return false;
    }
    SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
    format.setLenient(false);
    try {
      format.parse(s);
      return true;
    } catch (ParseException ex) {
      System.out.println(ex.getMessage());
      return false;
    }
  }
}
