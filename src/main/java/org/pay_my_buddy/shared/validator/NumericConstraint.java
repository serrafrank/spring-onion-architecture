package org.pay_my_buddy.shared.validator;

import java.util.function.Predicate;
import java.util.function.Supplier;

public class NumericConstraint extends ObjectConstraint<Number> {

    public NumericConstraint(Number value) {
        super(value);
    }

    public NumericConstraint isNotNull() {
        return (NumericConstraint) super.isNotNull();
    }

    public NumericConstraint isNotNull(String message) {
        return (NumericConstraint) super.isNotNull(message);
    }

    public NumericConstraint isNotNull(Supplier<Throwable> thowableSupplier) {
        return (NumericConstraint) super.isNotNull(thowableSupplier);
    }


    public NumericConstraint predicate(Predicate<Number> predicate) {
        return (NumericConstraint) super.predicate(predicate);
    }

    public NumericConstraint predicate(Predicate<Number> predicate, String message) {
        return (NumericConstraint) super.predicate(predicate, message);
    }

    public NumericConstraint predicate(Predicate<Number> predicate, Supplier<Throwable> thowableSupplier) {
        return (NumericConstraint) super.predicate(predicate, thowableSupplier);
    }

    public NumericConstraint isPositive() {
        validate(n -> n.doubleValue() > 0);
        return this;
    }

    public NumericConstraint isPositive(String message) {
        validate(() -> isPositive(), message);
        return this;
    }

    public NumericConstraint isPositive(Supplier<Throwable> thowableSupplier) {
        validate(() -> isPositive(), thowableSupplier);
        return this;
    }

    public NumericConstraint isNegative() {
        validate(n -> n.doubleValue() < 0);
        return this;
    }

    public NumericConstraint isNegative(String message) {
        validate(() -> isNegative(), message);
        return this;
    }

    public NumericConstraint isNegative(Supplier<Throwable> thowableSupplier) {
        validate(() -> isNegative(), thowableSupplier);
        return this;
    }

    public NumericConstraint isZero() {
        validate(n -> n.doubleValue() == 0);
        return this;
    }

    public NumericConstraint isZero(String message) {
        validate(() -> isZero(), message);
        return this;
    }

    public NumericConstraint isZero(Supplier<Throwable> thowableSupplier) {
        validate(() -> isZero(), thowableSupplier);
        return this;
    }

    public NumericConstraint isGreaterThan(Number value) {
        validate(n -> n.doubleValue() > value.doubleValue());
        return this;
    }

    public NumericConstraint isGreaterThan(Number value, String message) {
        validate(() -> isGreaterThan(value), message);
        return this;
    }

    public NumericConstraint isGreaterThan(Number value, Supplier<Throwable> thowableSupplier) {
        validate(() -> isGreaterThan(value), thowableSupplier);
        return this;
    }

    public NumericConstraint isGreaterThanOrEqualTo(Number value) {
        validate(n -> n.doubleValue() >= value.doubleValue());
        return this;
    }

    public NumericConstraint isGreaterThanOrEqualTo(Number value, String message) {
        validate(() -> isGreaterThanOrEqualTo(value), message);
        return this;
    }

    public NumericConstraint isGreaterThanOrEqualTo(Number value, Supplier<Throwable> thowableSupplier) {
        validate(() -> isGreaterThanOrEqualTo(value), thowableSupplier);
        return this;
    }

    public NumericConstraint isLessThan(Number value) {
        validate(n -> n.doubleValue() < value.doubleValue());
        return this;
    }

    public NumericConstraint isLessThan(Number value, String message) {
        validate(() -> isLessThan(value), message);
        return this;
    }

    public NumericConstraint isLessThan(Number value, Supplier<Throwable> thowableSupplier) {
        validate(() -> isLessThan(value), thowableSupplier);
        return this;
    }

    public NumericConstraint isLessThanOrEqualTo(Number value) {
        validate(n -> n.doubleValue() <= value.doubleValue());
        return this;
    }

    public NumericConstraint isLessThanOrEqualTo(Number value, String message) {
        validate(() -> isLessThanOrEqualTo(value), message);
        return this;
    }

    public NumericConstraint isLessThanOrEqualTo(Number value, Supplier<Throwable> thowableSupplier) {
        validate(() -> isLessThanOrEqualTo(value), thowableSupplier);
        return this;
    }

    public NumericConstraint isBetween(Number min, Number max) {
        validate(n -> n.doubleValue() >= min.doubleValue() && n.doubleValue() <= max.doubleValue());
        return this;
    }

    public NumericConstraint isBetween(Number min, Number max, String message) {
        validate(() -> isBetween(min, max), message);
        return this;
    }

    public NumericConstraint isBetween(Number min, Number max, Supplier<Throwable> thowableSupplier) {
        validate(() -> isBetween(min, max), thowableSupplier);
        return this;
    }

    public NumericConstraint isPositiveOrZero() {
        validate(n -> n.doubleValue() >= 0);
        return this;
    }

    public NumericConstraint isPositiveOrZero(String message) {
        validate(() -> isPositiveOrZero(), message);
        return this;
    }

    public NumericConstraint isPositiveOrZero(Supplier<Throwable> thowableSupplier) {
        validate(() -> isPositiveOrZero(), thowableSupplier);
        return this;
    }

    public NumericConstraint isNegativeOrZero() {
        validate(n -> n.doubleValue() <= 0);
        return this;
    }

    public NumericConstraint isNegativeOrZero(String message) {
        validate(() -> isNegativeOrZero(), message);
        return this;
    }

    public NumericConstraint isNegativeOrZero(Supplier<Throwable> thowableSupplier) {
        validate(() -> isNegativeOrZero(), thowableSupplier);
        return this;
    }

    public NumericConstraint isNotEqualTo(Number value) {
        validate(n -> n.doubleValue() != value.doubleValue());
        return this;
    }

    public NumericConstraint isNotEqualTo(Number value, String message) {
        validate(() -> isNotEqualTo(value), message);
        return this;
    }

    public NumericConstraint isNotEqualTo(Number value, Supplier<Throwable> thowableSupplier) {
        validate(() -> isNotEqualTo(value), thowableSupplier);
        return this;
    }

}
