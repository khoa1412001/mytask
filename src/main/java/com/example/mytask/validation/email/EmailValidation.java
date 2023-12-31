package com.example.mytask.validation.email;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = EmailValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailValidation {

  String message() default "Invalid email";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
