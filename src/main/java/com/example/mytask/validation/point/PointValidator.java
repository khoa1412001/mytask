package com.example.mytask.validation.point;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.*;

public class PointValidator implements ConstraintValidator<PointValidation, Integer> {

  @Override
  public void initialize(PointValidation constraintAnnotation) {
  }

  @Override
  public boolean isValid(Integer point, ConstraintValidatorContext constraintValidatorContext) {
    List<Integer> arr = new ArrayList<Integer>(5);
    arr.add(1);
    arr.add(2);
    arr.add(3);
    arr.add(5);
    arr.add(8);
    return arr.contains(point) && (point != null);
  }
}
