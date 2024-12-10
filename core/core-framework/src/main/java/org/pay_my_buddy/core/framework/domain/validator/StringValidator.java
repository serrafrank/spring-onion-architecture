package org.pay_my_buddy.core.framework.domain.validator;

import java.util.function.Predicate;
import java.util.function.Supplier;

public class StringValidator extends ObjectValidator<String> {
    private static final String EMAIL_REGEX = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

    public StringValidator(String value) {
        super(value);
    }

    public StringValidator predicate(Predicate<String> predicate) {
        return (StringValidator) super.predicate(predicate);
    }

    public StringValidator predicate(Predicate<String> predicate, String message) {
        return (StringValidator) super.predicate(predicate, message);
    }

    public StringValidator predicate(Predicate<String> predicate, Supplier<Throwable> thowableSupplier) {
        return (StringValidator) super.predicate(predicate, thowableSupplier);
    }

    public StringValidator isNotNull() {
        return (StringValidator) super.isNotNull();
    }

    public StringValidator isNotNull(String message) {
        return (StringValidator) super.isNotNull(message);
    }

    public StringValidator isNotNull(Supplier<Throwable> thowableSupplier) {
        return (StringValidator) super.isNotNull(thowableSupplier);
    }

    public StringValidator notBlank() {
        validate(s -> s != null && !s.isBlank());
        return this;
    }

    public StringValidator notBlank(String message) {
        validate(() -> notBlank(), message);
        return this;
    }

    public StringValidator notBlank(Supplier<Throwable> thowableSupplier) {
        validate(() -> notBlank(), thowableSupplier);
        return this;
    }

    public StringValidator notEmpty() {
        validate(s -> s != null && !s.isEmpty());
        return this;
    }

    public StringValidator notEmpty(String message) {
        validate(() -> notEmpty(), message);
        return this;
    }

    public StringValidator notEmpty(Supplier<Throwable> thowableSupplier) {
        validate(() -> notEmpty(), thowableSupplier);
        return this;
    }

    public StringValidator length(int length) {
        validate(s -> s.length() == length);
        return this;
    }

    public StringValidator length(int length, String message) {
        validate(() -> length(length), message);
        return this;
    }

    public StringValidator length(int length, Supplier<Throwable> thowableSupplier) {
        validate(() -> length(length), thowableSupplier);
        return this;
    }

    public StringValidator minLength(int length) {
        validate(s -> s.length() >= length);
        return this;
    }

    public StringValidator minLength(int length, String message) {
        validate(() -> minLength(length), message);
        return this;
    }

    public StringValidator minLength(int length, Supplier<Throwable> thowableSupplier) {
        validate(() -> minLength(length), thowableSupplier);
        return this;
    }

    public StringValidator maxLength(int length) {
        validate(s -> s.length() <= length);
        return this;
    }

    public StringValidator maxLength(int length, String message) {
        validate(() -> maxLength(length), message);
        return this;
    }

    public StringValidator maxLength(int length, Supplier<Throwable> thowableSupplier) {
        validate(() -> maxLength(length), thowableSupplier);
        return this;
    }

    public StringValidator matches(String regex) {
        validate(s -> s.matches(regex));
        return this;
    }

    public StringValidator matches(String regex, String message) {
        validate(() -> matches(regex), message);
        return this;
    }

    public StringValidator matches(String regex, Supplier<Throwable> thowableSupplier) {
        validate(() -> matches(regex), thowableSupplier);
        return this;
    }

    public StringValidator contains(String sequence) {
        validate(s -> s.contains(sequence));
        return this;
    }

    public StringValidator contains(String sequence, String message) {
        validate(() -> contains(sequence), message);
        return this;
    }

    public StringValidator contains(String sequence, Supplier<Throwable> thowableSupplier) {
        validate(() -> contains(sequence), thowableSupplier);
        return this;
    }

    public StringValidator startsWith(String sequence) {
        validate(s -> s.startsWith(sequence));
        return this;
    }

    public StringValidator startsWith(String sequence, String message) {
        validate(() -> startsWith(sequence), message);
        return this;
    }

    public StringValidator startsWith(String sequence, Supplier<Throwable> thowableSupplier) {
        validate(() -> startsWith(sequence), thowableSupplier);
        return this;
    }

    public StringValidator endsWith(String sequence) {
        validate(s -> s.endsWith(sequence));
        return this;
    }

    public StringValidator endsWith(String sequence, String message) {
        validate(() -> endsWith(sequence), message);
        return this;
    }

    public StringValidator endsWith(String sequence, Supplier<Throwable> thowableSupplier) {
        validate(() -> endsWith(sequence), thowableSupplier);
        return this;
    }

    public StringValidator isLowerCase() {
        validate(s -> s.equals(s.toLowerCase()));
        return this;
    }

    public StringValidator isLowerCase(String message) {
        validate(() -> isLowerCase(), message);
        return this;
    }

    public StringValidator isLowerCase(Supplier<Throwable> thowableSupplier) {
        validate(() -> isLowerCase(), thowableSupplier);
        return this;
    }

    public StringValidator isUpperCase() {
        validate(s -> s.equals(s.toUpperCase()));
        return this;
    }

    public StringValidator isUpperCase(String message) {
        validate(() -> isUpperCase(), message);
        return this;
    }

    public StringValidator isUpperCase(Supplier<Throwable> thowableSupplier) {
        validate(() -> isUpperCase(), thowableSupplier);
        return this;
    }

    public StringValidator email() {
        return email("email is not valid with value = " + value);
    }

    public StringValidator email(String message) {
        validate(s -> s.matches(EMAIL_REGEX), message);
        return this;
    }

    public StringValidator email(Supplier<Throwable> thowableSupplier) {
        validate(s -> s.matches(EMAIL_REGEX), thowableSupplier);
        return this;
    }

}
