package org.pay_my_buddy.shared.domain.validator;

import java.util.function.Predicate;
import java.util.function.Supplier;

public class NumericConstraint extends ObjectConstraint<Number> {

    private static final Predicate<Number> IS_POSITIVE = n -> n.doubleValue() > 0;
    private static final Predicate<Number> IS_NEGATIVE = n -> n.doubleValue() < 0;
    private static final Predicate<Number> IS_ZERO = n -> n.doubleValue() == 0;
    private static final Predicate<Number> IS_GREATER_THAN = n -> n.doubleValue() > 0;
    private static final Predicate<Number> IS_GREATER_THAN_OR_EQUAL_TO = n -> n.doubleValue() >= 0;
    private static final Predicate<Number> IS_LESS_THAN = n -> n.doubleValue() < 0;
    private static final Predicate<Number> IS_LESS_THAN_OR_EQUAL_TO = n -> n.doubleValue() <= 0;
    private static final Predicate<Number> IS_BETWEEN = n -> n.doubleValue() >= 0;
    private static final Predicate<Number> IS_POSITIVE_OR_ZERO = n -> n.doubleValue() >= 0;
    private static final Predicate<Number> IS_NEGATIVE_OR_ZERO = n -> n.doubleValue() <= 0;
    private static final Predicate<Number> IS_NOT_EQUAL_TO = n -> n.doubleValue() != 0;


    public NumericConstraint(Number value) {
        super(value);
    }

    public NumericConstraint isPositive() {
        validate(IS_POSITIVE);
        return this;
    }

    public NumericConstraint isPositive(String message) {
        validate(IS_POSITIVE, message);
        return this;
    }

    public NumericConstraint isPositive(Supplier<Throwable> thowableSupplier) {
        validate(IS_POSITIVE, thowableSupplier);
        return this;
    }

    public NumericConstraint isNegative() {
        validate(IS_NEGATIVE);
        return this;
    }

    public NumericConstraint isNegative(String message) {
        validate(IS_NEGATIVE, message);
        return this;
    }

    public NumericConstraint isNegative(Supplier<Throwable> thowableSupplier) {
        validate(IS_NEGATIVE, thowableSupplier);
        return this;
    }

    public NumericConstraint isZero() {
        validate(IS_ZERO);
        return this;
    }

    public NumericConstraint isZero(String message) {
        validate(IS_ZERO, message);
        return this;
    }

    public NumericConstraint isZero(Supplier<Throwable> thowableSupplier) {
        validate(IS_ZERO, thowableSupplier);
        return this;
    }

    public NumericConstraint isGreaterThan(Number value) {
        validate(IS_GREATER_THAN);
        return this;
    }

    public NumericConstraint isGreaterThan(Number value, String message) {
        validate(IS_GREATER_THAN, message);
        return this;
    }

    public NumericConstraint isGreaterThan(Number value, Supplier<Throwable> thowableSupplier) {
        validate(IS_GREATER_THAN, thowableSupplier);
        return this;
    }

    public NumericConstraint isGreaterThanOrEqualTo(Number value) {
        validate(IS_GREATER_THAN_OR_EQUAL_TO);
        return this;
    }

    public NumericConstraint isGreaterThanOrEqualTo(Number value, String message) {
        validate(IS_GREATER_THAN_OR_EQUAL_TO, message);
        return this;
    }

    public NumericConstraint isGreaterThanOrEqualTo(Number value, Supplier<Throwable> thowableSupplier) {
        validate(IS_GREATER_THAN_OR_EQUAL_TO, thowableSupplier);
        return this;
    }

    public NumericConstraint isLessThan(Number value) {
        validate(IS_LESS_THAN);
        return this;
    }

    public NumericConstraint isLessThan(Number value, String message) {
        validate(IS_LESS_THAN, message);
        return this;
    }

    public NumericConstraint isLessThan(Number value, Supplier<Throwable> thowableSupplier) {
        validate(IS_LESS_THAN, thowableSupplier);
        return this;
    }

    public NumericConstraint isLessThanOrEqualTo(Number value) {
        validate(IS_LESS_THAN_OR_EQUAL_TO);
        return this;
    }

    public NumericConstraint isLessThanOrEqualTo(Number value, String message) {
        validate(IS_LESS_THAN_OR_EQUAL_TO, message);
        return this;
    }

    public NumericConstraint isLessThanOrEqualTo(Number value, Supplier<Throwable> thowableSupplier) {
        validate(IS_LESS_THAN_OR_EQUAL_TO, thowableSupplier);
        return this;
    }

    public NumericConstraint isBetween(Number min, Number max) {
        validate(IS_BETWEEN);
        return this;
    }

    public NumericConstraint isBetween(Number min, Number max, String message) {
        validate(IS_BETWEEN, message);
        return this;
    }

    public NumericConstraint isBetween(Number min, Number max, Supplier<Throwable> thowableSupplier) {
        validate(IS_BETWEEN, thowableSupplier);
        return this;
    }

    public NumericConstraint isPositiveOrZero() {
        validate(IS_POSITIVE_OR_ZERO);
        return this;
    }

    public NumericConstraint isPositiveOrZero(String message) {
        validate(IS_POSITIVE_OR_ZERO, message);
        return this;
    }

    public NumericConstraint isPositiveOrZero(Supplier<Throwable> thowableSupplier) {
        validate(IS_POSITIVE_OR_ZERO, thowableSupplier);
        return this;
    }

    public NumericConstraint isNegativeOrZero() {
        validate(IS_NEGATIVE_OR_ZERO);
        return this;
    }

    public NumericConstraint isNegativeOrZero(String message) {
        validate(IS_NEGATIVE_OR_ZERO, message);
        return this;
    }

    public NumericConstraint isNegativeOrZero(Supplier<Throwable> thowableSupplier) {
        validate(IS_NEGATIVE_OR_ZERO, thowableSupplier);
        return this;
    }

    public NumericConstraint isNotEqualTo(Number value) {
        validate(IS_NOT_EQUAL_TO);
        return this;
    }

    public NumericConstraint isNotEqualTo(Number value, String message) {
        validate(IS_NOT_EQUAL_TO, message);
        return this;
    }

    public NumericConstraint isNotEqualTo(Number value, Supplier<Throwable> thowableSupplier) {
        validate(IS_NOT_EQUAL_TO, thowableSupplier);
        return this;
    }

}