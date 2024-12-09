package org.pay_my_buddy.shared.validator;

import java.util.function.Predicate;
import java.util.function.Supplier;

public class StringConstraint extends ObjectConstraint<String> {
    private static final String EMAIL_REGEX = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

    public StringConstraint(String value) {
        super(value);
    }

    public StringConstraint predicate(Predicate<String> predicate) {
        return (StringConstraint) super.predicate(predicate);
    }

    public StringConstraint predicate(Predicate<String> predicate, String message) {
        return (StringConstraint) super.predicate(predicate, message);
    }

    public StringConstraint predicate(Predicate<String> predicate, Supplier<Throwable> thowableSupplier) {
        return (StringConstraint) super.predicate(predicate, thowableSupplier);
    }

    public StringConstraint isNotNull() {
        return (StringConstraint) super.isNotNull();
    }

    public StringConstraint isNotNull(String message) {
        return (StringConstraint) super.isNotNull(message);
    }

    public StringConstraint isNotNull(Supplier<Throwable> thowableSupplier) {
        return (StringConstraint) super.isNotNull(thowableSupplier);
    }

    public StringConstraint notBlank() {
        validate(s -> s != null && !s.isBlank());
        return this;
    }

    public StringConstraint notBlank(String message) {
        validate(() -> notBlank(), message);
        return this;
    }

    public StringConstraint notBlank(Supplier<Throwable> thowableSupplier) {
        validate(() -> notBlank(), thowableSupplier);
        return this;
    }

    public StringConstraint notEmpty() {
        validate(s -> s != null && !s.isEmpty());
        return this;
    }

    public StringConstraint notEmpty(String message) {
        validate(() -> notEmpty(), message);
        return this;
    }

    public StringConstraint notEmpty(Supplier<Throwable> thowableSupplier) {
        validate(() -> notEmpty(), thowableSupplier);
        return this;
    }

    public StringConstraint length(int length) {
        validate(s -> s.length() == length);
        return this;
    }

    public StringConstraint length(int length, String message) {
        validate(() -> length(length), message);
        return this;
    }

    public StringConstraint length(int length, Supplier<Throwable> thowableSupplier) {
        validate(() -> length(length), thowableSupplier);
        return this;
    }

    public StringConstraint minLength(int length) {
        validate(s -> s.length() >= length);
        return this;
    }

    public StringConstraint minLength(int length, String message) {
        validate(() -> minLength(length), message);
        return this;
    }

    public StringConstraint minLength(int length, Supplier<Throwable> thowableSupplier) {
        validate(() -> minLength(length), thowableSupplier);
        return this;
    }

    public StringConstraint maxLength(int length) {
        validate(s -> s.length() <= length);
        return this;
    }

    public StringConstraint maxLength(int length, String message) {
        validate(() -> maxLength(length), message);
        return this;
    }

    public StringConstraint maxLength(int length, Supplier<Throwable> thowableSupplier) {
        validate(() -> maxLength(length), thowableSupplier);
        return this;
    }

    public StringConstraint matches(String regex) {
        validate(s -> s.matches(regex));
        return this;
    }

    public StringConstraint matches(String regex, String message) {
        validate(() -> matches(regex), message);
        return this;
    }

    public StringConstraint matches(String regex, Supplier<Throwable> thowableSupplier) {
        validate(() -> matches(regex), thowableSupplier);
        return this;
    }

    public StringConstraint contains(String sequence) {
        validate(s -> s.contains(sequence));
        return this;
    }

    public StringConstraint contains(String sequence, String message) {
        validate(() -> contains(sequence), message);
        return this;
    }

    public StringConstraint contains(String sequence, Supplier<Throwable> thowableSupplier) {
        validate(() -> contains(sequence), thowableSupplier);
        return this;
    }

    public StringConstraint startsWith(String sequence) {
        validate(s -> s.startsWith(sequence));
        return this;
    }

    public StringConstraint startsWith(String sequence, String message) {
        validate(() -> startsWith(sequence), message);
        return this;
    }

    public StringConstraint startsWith(String sequence, Supplier<Throwable> thowableSupplier) {
        validate(() -> startsWith(sequence), thowableSupplier);
        return this;
    }

    public StringConstraint endsWith(String sequence) {
        validate(s -> s.endsWith(sequence));
        return this;
    }

    public StringConstraint endsWith(String sequence, String message) {
        validate(() -> endsWith(sequence), message);
        return this;
    }

    public StringConstraint endsWith(String sequence, Supplier<Throwable> thowableSupplier) {
        validate(() -> endsWith(sequence), thowableSupplier);
        return this;
    }

    public StringConstraint isLowerCase() {
        validate(s -> s.equals(s.toLowerCase()));
        return this;
    }

    public StringConstraint isLowerCase(String message) {
        validate(() -> isLowerCase(), message);
        return this;
    }

    public StringConstraint isLowerCase(Supplier<Throwable> thowableSupplier) {
        validate(() -> isLowerCase(), thowableSupplier);
        return this;
    }

    public StringConstraint isUpperCase() {
        validate(s -> s.equals(s.toUpperCase()));
        return this;
    }

    public StringConstraint isUpperCase(String message) {
        validate(() -> isUpperCase(), message);
        return this;
    }

    public StringConstraint isUpperCase(Supplier<Throwable> thowableSupplier) {
        validate(() -> isUpperCase(), thowableSupplier);
        return this;
    }

    public StringConstraint email() {
        return email("email is not valid with value = " + value);
    }

    public StringConstraint email(String message) {
        validate(s -> s.matches(EMAIL_REGEX), message);
        return this;
    }

    public StringConstraint email(Supplier<Throwable> thowableSupplier) {
        validate(s -> s.matches(EMAIL_REGEX), thowableSupplier);
        return this;
    }

}
