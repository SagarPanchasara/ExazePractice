package com.example.practical.validator;

import com.example.practical.util.IDNumberParser;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IdNumberValidator implements ConstraintValidator<IdNumberConstraint, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return IDNumberParser.isValid(s);
    }
}
