package com.example.paymybuddy.core.common.valueobject;

import jakarta.validation.*;

import java.util.Set;

public class ValueObject {

    protected void validate() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<ValueObject>> violations = validator.validate(this);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

    }
}
