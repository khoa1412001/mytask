package com.example.mytask.validation.point;

import static com.example.mytask.constant.CONSTANT.POINT_LIST;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.*;

public class PointValidator implements ConstraintValidator<PointValidation, Integer> {

  @Override
  public void initialize(PointValidation constraintAnnotation) {
  }

  @Override
  public boolean isValid(Integer point, ConstraintValidatorContext constraintValidatorContext) {
    return POINT_LIST.contains(point) && (point != null);
  }
}
