package com.example.paymybuddy.core.common.valueobject;

import jakarta.validation.*;

import java.util.Set;

public class ValueObject {

    protected static <T> void validate(T target) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<T>> violations = validator.validate(target);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}
