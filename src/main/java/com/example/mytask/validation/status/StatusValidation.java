package com.example.mytask.validation.status;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = StatusValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface StatusValidation {

  String message() default "Invalid point";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
