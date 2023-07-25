package com.example.mytask.validation.point;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = PointValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface PointValidation {

  String message() default "Invalid point";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
