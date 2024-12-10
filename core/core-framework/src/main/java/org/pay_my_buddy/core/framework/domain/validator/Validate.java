package org.pay_my_buddy.core.framework.domain.validator;


public class Validate {

    public static <O> ObjectValidator<O> checkIf(O value) {
        return new ObjectValidator<>(value);
    }

    public static NumericValidator checkIf(Number value) {
        return new NumericValidator(value);
    }

    public static StringValidator checkIf(String value) {
        return new StringValidator(value);
    }
}
