package org.pay_my_buddy.core.framework.domain.validator;

import java.util.function.Predicate;
import java.util.function.Supplier;

public class NumericValidator extends ObjectValidator<Number> {

    public NumericValidator(Number value) {
        super(value);
    }

    public NumericValidator isNotNull() {
        return (NumericValidator) super.isNotNull();
    }

    public NumericValidator isNotNull(String message) {
        return (NumericValidator) super.isNotNull(message);
    }

    public NumericValidator isNotNull(Supplier<Throwable> thowableSupplier) {
        return (NumericValidator) super.isNotNull(thowableSupplier);
    }


    public NumericValidator predicate(Predicate<Number> predicate) {
        return (NumericValidator) super.predicate(predicate);
    }

    public NumericValidator predicate(Predicate<Number> predicate, String message) {
        return (NumericValidator) super.predicate(predicate, message);
    }

    public NumericValidator predicate(Predicate<Number> predicate, Supplier<Throwable> thowableSupplier) {
        return (NumericValidator) super.predicate(predicate, thowableSupplier);
    }

    public NumericValidator isPositive() {
        validate(n -> n.doubleValue() > 0);
        return this;
    }

    public NumericValidator isPositive(String message) {
        validate(() -> isPositive(), message);
        return this;
    }

    public NumericValidator isPositive(Supplier<Throwable> thowableSupplier) {
        validate(() -> isPositive(), thowableSupplier);
        return this;
    }

    public NumericValidator isNegative() {
        validate(n -> n.doubleValue() < 0);
        return this;
    }

    public NumericValidator isNegative(String message) {
        validate(() -> isNegative(), message);
        return this;
    }

    public NumericValidator isNegative(Supplier<Throwable> thowableSupplier) {
        validate(() -> isNegative(), thowableSupplier);
        return this;
    }

    public NumericValidator isZero() {
        validate(n -> n.doubleValue() == 0);
        return this;
    }

    public NumericValidator isZero(String message) {
        validate(() -> isZero(), message);
        return this;
    }

    public NumericValidator isZero(Supplier<Throwable> thowableSupplier) {
        validate(() -> isZero(), thowableSupplier);
        return this;
    }

    public NumericValidator isGreaterThan(Number value) {
        validate(n -> n.doubleValue() > value.doubleValue());
        return this;
    }

    public NumericValidator isGreaterThan(Number value, String message) {
        validate(() -> isGreaterThan(value), message);
        return this;
    }

    public NumericValidator isGreaterThan(Number value, Supplier<Throwable> thowableSupplier) {
        validate(() -> isGreaterThan(value), thowableSupplier);
        return this;
    }

    public NumericValidator isGreaterThanOrEqualTo(Number value) {
        validate(n -> n.doubleValue() >= value.doubleValue());
        return this;
    }

    public NumericValidator isGreaterThanOrEqualTo(Number value, String message) {
        validate(() -> isGreaterThanOrEqualTo(value), message);
        return this;
    }

    public NumericValidator isGreaterThanOrEqualTo(Number value, Supplier<Throwable> thowableSupplier) {
        validate(() -> isGreaterThanOrEqualTo(value), thowableSupplier);
        return this;
    }

    public NumericValidator isLessThan(Number value) {
        validate(n -> n.doubleValue() < value.doubleValue());
        return this;
    }

    public NumericValidator isLessThan(Number value, String message) {
        validate(() -> isLessThan(value), message);
        return this;
    }

    public NumericValidator isLessThan(Number value, Supplier<Throwable> thowableSupplier) {
        validate(() -> isLessThan(value), thowableSupplier);
        return this;
    }

    public NumericValidator isLessThanOrEqualTo(Number value) {
        validate(n -> n.doubleValue() <= value.doubleValue());
        return this;
    }

    public NumericValidator isLessThanOrEqualTo(Number value, String message) {
        validate(() -> isLessThanOrEqualTo(value), message);
        return this;
    }

    public NumericValidator isLessThanOrEqualTo(Number value, Supplier<Throwable> thowableSupplier) {
        validate(() -> isLessThanOrEqualTo(value), thowableSupplier);
        return this;
    }

    public NumericValidator isBetween(Number min, Number max) {
        validate(n -> n.doubleValue() >= min.doubleValue() && n.doubleValue() <= max.doubleValue());
        return this;
    }

    public NumericValidator isBetween(Number min, Number max, String message) {
        validate(() -> isBetween(min, max), message);
        return this;
    }

    public NumericValidator isBetween(Number min, Number max, Supplier<Throwable> thowableSupplier) {
        validate(() -> isBetween(min, max), thowableSupplier);
        return this;
    }

    public NumericValidator isPositiveOrZero() {
        validate(n -> n.doubleValue() >= 0);
        return this;
    }

    public NumericValidator isPositiveOrZero(String message) {
        validate(() -> isPositiveOrZero(), message);
        return this;
    }

    public NumericValidator isPositiveOrZero(Supplier<Throwable> thowableSupplier) {
        validate(() -> isPositiveOrZero(), thowableSupplier);
        return this;
    }

    public NumericValidator isNegativeOrZero() {
        validate(n -> n.doubleValue() <= 0);
        return this;
    }

    public NumericValidator isNegativeOrZero(String message) {
        validate(() -> isNegativeOrZero(), message);
        return this;
    }

    public NumericValidator isNegativeOrZero(Supplier<Throwable> thowableSupplier) {
        validate(() -> isNegativeOrZero(), thowableSupplier);
        return this;
    }

    public NumericValidator isNotEqualTo(Number value) {
        validate(n -> n.doubleValue() != value.doubleValue());
        return this;
    }

    public NumericValidator isNotEqualTo(Number value, String message) {
        validate(() -> isNotEqualTo(value), message);
        return this;
    }

    public NumericValidator isNotEqualTo(Number value, Supplier<Throwable> thowableSupplier) {
        validate(() -> isNotEqualTo(value), thowableSupplier);
        return this;
    }

}
