package org.pay_my_buddy.shared.domain.validator;

import java.util.function.Predicate;
import java.util.function.Supplier;

public class StringConstraint extends ObjectConstraint<String> {

    public static final Predicate<String> IS_LOWERCASE = s -> s != null && s.equals(s.toLowerCase());
    public static final Predicate<String> IS_UPPERCASE = s -> s != null && s.equals(s.toUpperCase());
    public static final String EMAIL_REGEX = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    private static final Predicate<String> NOT_BLANK = s -> s != null && !s.isBlank();
    private static final Predicate<String> NOT_EMPTY = s -> s != null && !s.isEmpty();


    public StringConstraint(String value) {
        super(value);
    }


    public StringConstraint notBlank() {
        validate(NOT_BLANK);
        return this;
    }

    public StringConstraint notBlank(String message) {
        validate(NOT_BLANK, message);
        return this;
    }

    public StringConstraint notBlank(Supplier<Throwable> thowableSupplier) {
        validate(NOT_BLANK, thowableSupplier);
        return this;
    }

    public StringConstraint notEmpty() {
        validate(NOT_EMPTY);
        return this;
    }

    public StringConstraint notEmpty(String message) {
        validate(NOT_EMPTY, message);
        return this;
    }

    public StringConstraint notEmpty(Supplier<Throwable> thowableSupplier) {
        validate(NOT_EMPTY, thowableSupplier);
        return this;
    }

    public StringConstraint length(int length) {
        validate(s -> s.length() == length);
        return this;
    }

    public StringConstraint length(int length, String message) {
        validate(s -> s.length() == length, message);
        return this;
    }

    public StringConstraint length(int length, Supplier<Throwable> thowableSupplier) {
        validate(s -> s.length() == length, thowableSupplier);
        return this;
    }

    public StringConstraint minLength(int length) {
        validate(s -> s.length() >= length);
        return this;
    }

    public StringConstraint minLength(int length, String message) {
        validate(s -> s.length() >= length, message);
        return this;
    }

    public StringConstraint minLength(int length, Supplier<Throwable> thowableSupplier) {
        validate(s -> s.length() >= length, thowableSupplier);
        return this;
    }

    public StringConstraint maxLength(int length) {
        validate(s -> s.length() <= length);
        return this;
    }

    public StringConstraint maxLength(int length, String message) {
        validate(s -> s.length() <= length, message);
        return this;
    }

    public StringConstraint maxLength(int length, Supplier<Throwable> thowableSupplier) {
        validate(s -> s.length() <= length, thowableSupplier);
        return this;
    }

    public StringConstraint matches(String regex) {
        validate(s -> s.matches(regex));
        return this;
    }

    public StringConstraint matches(String regex, String message) {
        validate(s -> s.matches(regex), message);
        return this;
    }

    public StringConstraint matches(String regex, Supplier<Throwable> thowableSupplier) {
        validate(s -> s.matches(regex), thowableSupplier);
        return this;
    }

    public StringConstraint contains(String sequence) {
        validate(s -> s.contains(sequence));
        return this;
    }

    public StringConstraint contains(String sequence, String message) {
        validate(s -> s.contains(sequence), message);
        return this;
    }

    public StringConstraint contains(String sequence, Supplier<Throwable> thowableSupplier) {
        validate(s -> s.contains(sequence), thowableSupplier);
        return this;
    }

    public StringConstraint startsWith(String sequence) {
        validate(s -> s.startsWith(sequence));
        return this;
    }

    public StringConstraint startsWith(String sequence, String message) {
        validate(s -> s.startsWith(sequence), message);
        return this;
    }

    public StringConstraint startsWith(String sequence, Supplier<Throwable> thowableSupplier) {
        validate(s -> s.startsWith(sequence), thowableSupplier);
        return this;
    }

    public StringConstraint endsWith(String sequence) {
        validate(s -> s.endsWith(sequence));
        return this;
    }

    public StringConstraint endsWith(String sequence, String message) {
        validate(s -> s.endsWith(sequence), message);
        return this;
    }

    public StringConstraint endsWith(String sequence, Supplier<Throwable> thowableSupplier) {
        validate(s -> s.endsWith(sequence), thowableSupplier);
        return this;
    }

    public StringConstraint isLowerCase() {
        validate(IS_LOWERCASE);
        return this;
    }

    public StringConstraint isLowerCase(String message) {
        validate(IS_LOWERCASE, message);
        return this;
    }

    public StringConstraint isLowerCase(Supplier<Throwable> thowableSupplier) {
        validate(IS_LOWERCASE, thowableSupplier);
        return this;
    }

    public StringConstraint isUpperCase() {
        validate(IS_UPPERCASE);
        return this;
    }

    public StringConstraint isUpperCase(String message) {
        validate(IS_UPPERCASE, message);
        return this;
    }

    public StringConstraint isUpperCase(Supplier<Throwable> thowableSupplier) {
        validate(IS_UPPERCASE, thowableSupplier);
        return this;
    }

    public StringConstraint email() {
        return email("email is not valid.");
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