package com.example.paymybuddy.core.validator;

import com.example.paymybuddy.core.validator.constraintpredicate.NumericConstraintPredicate;
import com.example.paymybuddy.core.validator.constraintpredicate.ObjectConstraintPredicate;
import com.example.paymybuddy.core.validator.constraintpredicate.StringConstraintPredicate;

import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;

public class Constraint {

    public static ObjectConstraintPredicate<?> of(Object value) {
        return new ObjectConstraintPredicate<>(value);
    }

    public static NumericConstraintPredicate of(Number value) {
        return new NumericConstraintPredicate(value);
    }

    public static StringConstraintPredicate of(String value) {
        return new StringConstraintPredicate(value);
    }
}
