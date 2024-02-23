package com.example.paymybuddy.core.validator.constraintpredicate;

public class NumericConstraintPredicate extends ObjectConstraintPredicate<Number> {
    public NumericConstraintPredicate(Number value) {
        super(value);
    }

    public NumericConstraintPredicate isPositive() {
        this.constraints.add(new ConstraintPredicate<>(n -> n.doubleValue() > 0));
        return this;
    }

    public NumericConstraintPredicate isPositive(String message) {
        this.constraints.add(new ConstraintPredicate<>(n -> n.doubleValue() > 0, message));
        return this;
    }

    public NumericConstraintPredicate isPositive(Exception exception) {
        this.constraints.add(new ConstraintPredicate<>(n -> n.doubleValue() > 0, exception));
        return this;
    }

    public NumericConstraintPredicate isNegative() {
        this.constraints.add(new ConstraintPredicate<>(n -> n.doubleValue() < 0));
        return this;
    }

    public NumericConstraintPredicate isNegative(String message) {
        this.constraints.add(new ConstraintPredicate<>(n -> n.doubleValue() < 0, message));
        return this;
    }

    public NumericConstraintPredicate isNegative(Exception exception) {
        this.constraints.add(new ConstraintPredicate<>(n -> n.doubleValue() < 0, exception));
        return this;
    }

    public NumericConstraintPredicate isZero() {
        this.constraints.add(new ConstraintPredicate<>(n -> n.doubleValue() == 0));
        return this;
    }

    public NumericConstraintPredicate isZero(String message) {
        this.constraints.add(new ConstraintPredicate<>(n -> n.doubleValue() == 0, message));
        return this;
    }

    public NumericConstraintPredicate isZero(Exception exception) {
        this.constraints.add(new ConstraintPredicate<>(n -> n.doubleValue() == 0, exception));
        return this;
    }

    public NumericConstraintPredicate isGreaterThan(Number number) {
        this.constraints.add(new ConstraintPredicate<>(n -> n.doubleValue() > number.doubleValue()));
        return this;
    }

    public NumericConstraintPredicate isGreaterThan(Number number, String message) {
        this.constraints.add(new ConstraintPredicate<>(n -> n.doubleValue() > number.doubleValue(), message));
        return this;
    }

    public NumericConstraintPredicate isGreaterThan(Number number, Exception exception) {
        this.constraints.add(new ConstraintPredicate<>(n -> n.doubleValue() > number.doubleValue(), exception));
        return this;
    }

    public NumericConstraintPredicate isGreaterThanOrEqualTo(Number number) {
        this.constraints.add(new ConstraintPredicate<>(n -> n.doubleValue() >= number.doubleValue()));
        return this;
    }

    public NumericConstraintPredicate isGreaterThanOrEqualTo(Number number, String message) {
        this.constraints.add(new ConstraintPredicate<>(n -> n.doubleValue() >= number.doubleValue(), message));
        return this;
    }

    public NumericConstraintPredicate isGreaterThanOrEqualTo(Number number, Exception exception) {
        this.constraints.add(new ConstraintPredicate<>(n -> n.doubleValue() >= number.doubleValue(), exception));
        return this;
    }


    public NumericConstraintPredicate isLessThan(Number number) {
        this.constraints.add(new ConstraintPredicate<>(n -> n.doubleValue() < number.doubleValue()));
        return this;
    }

    public NumericConstraintPredicate isLessThan(Number number, String message) {
        this.constraints.add(new ConstraintPredicate<>(n -> n.doubleValue() < number.doubleValue(), message));
        return this;
    }

    public NumericConstraintPredicate isLessThan(Number number, Exception exception) {
        this.constraints.add(new ConstraintPredicate<>(n -> n.doubleValue() < number.doubleValue(), exception));
        return this;
    }

    public NumericConstraintPredicate isLessThanOrEqualTo(Number number) {
        this.constraints.add(new ConstraintPredicate<>(n -> n.doubleValue() <= number.doubleValue()));
        return this;
    }

    public NumericConstraintPredicate isLessThanOrEqualTo(Number number, String message) {
        this.constraints.add(new ConstraintPredicate<>(n -> n.doubleValue() <= number.doubleValue(), message));
        return this;
    }

    public NumericConstraintPredicate isLessThanOrEqualTo(Number number, Exception exception) {
        this.constraints.add(new ConstraintPredicate<>(n -> n.doubleValue() <= number.doubleValue(), exception));
        return this;
    }

    public NumericConstraintPredicate isBetween(Number min, Number max) {
        this.constraints.add(new ConstraintPredicate<>(n -> n.doubleValue() >= min.doubleValue() && n.doubleValue() <= max.doubleValue()));
        return this;
    }

    public NumericConstraintPredicate isBetween(Number min, Number max, String message) {
        this.constraints.add(new ConstraintPredicate<>(n -> n.doubleValue() >= min.doubleValue() && n.doubleValue() <= max.doubleValue(), message));
        return this;
    }


    public NumericConstraintPredicate isBetween(Number min, Number max, Exception exception) {
        this.constraints.add(new ConstraintPredicate<>(n -> n.doubleValue() >= min.doubleValue() && n.doubleValue() <= max.doubleValue(), exception));
        return this;
    }

    public NumericConstraintPredicate isPositiveOrZero() {
        this.constraints.add(new ConstraintPredicate<>(n -> n.doubleValue() >= 0));
        return this;
    }

    public NumericConstraintPredicate isPositiveOrZero(String message) {
        this.constraints.add(new ConstraintPredicate<>(n -> n.doubleValue() >= 0, message));
        return this;
    }

    public NumericConstraintPredicate isPositiveOrZero(Exception exception) {
        this.constraints.add(new ConstraintPredicate<>(n -> n.doubleValue() >= 0, exception));
        return this;
    }

    public NumericConstraintPredicate isNegativeOrZero() {
        this.constraints.add(new ConstraintPredicate<>(n -> n.doubleValue() <= 0));
        return this;
    }

    public NumericConstraintPredicate isNegativeOrZero(String message) {
        this.constraints.add(new ConstraintPredicate<>(n -> n.doubleValue() <= 0, message));
        return this;
    }

    public NumericConstraintPredicate isNegativeOrZero(Exception exception) {
        this.constraints.add(new ConstraintPredicate<>(n -> n.doubleValue() <= 0, exception));
        return this;
    }

    public NumericConstraintPredicate isNotEqualTo(Number number) {
        this.constraints.add(new ConstraintPredicate<>(n -> n.doubleValue() != number.doubleValue()));
        return this;
    }

    public NumericConstraintPredicate isNotEqualTo(Number number, String message) {
        this.constraints.add(new ConstraintPredicate<>(n -> n.doubleValue() != number.doubleValue(), message));
        return this;
    }

    public NumericConstraintPredicate isNotEqualTo(Number number, Exception exception) {
        this.constraints.add(new ConstraintPredicate<>(n -> n.doubleValue() != number.doubleValue(), exception));
        return this;
    }
}