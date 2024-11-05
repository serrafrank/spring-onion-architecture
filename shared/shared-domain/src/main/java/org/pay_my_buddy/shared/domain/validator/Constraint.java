package org.pay_my_buddy.shared.domain.validator;


public class Constraint {

    public static <O> ObjectConstraint<O> checkIf(O value) {
        return new ObjectConstraint<>(value);
    }

    public static NumericConstraint checkIf(Number value) {
        return new NumericConstraint(value);
    }

    public static StringConstraint checkIf(String value) {
        return new StringConstraint(value);
    }
}
