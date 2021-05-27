package com.example.practical.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = IdNumberValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IdNumberConstraint {

    String message() default "Invalid id number pattern";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
