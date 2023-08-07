package com.example.mytask.validation.dob;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = DobValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface DobValidation {

  String message() default "Invalid dob";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
