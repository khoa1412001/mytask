package com.example.mytask.validation.timestamp;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Constraint(validatedBy = DateValidator.class)
public @interface DateValidation {

  String message() default "Invalid time";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
