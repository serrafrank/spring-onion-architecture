package com.example.paymybuddy.core.validator.constraintpredicate;

public class StringConstraintPredicate extends ObjectConstraintPredicate<String> {
    public StringConstraintPredicate(String value) {
        super(value);
    }


    public StringConstraintPredicate notBlank() {
        this.constraints.add(new ConstraintPredicate<>(s -> !s.isBlank()));
        return this;
    }

    public StringConstraintPredicate notBlank(String message) {
        this.constraints.add(new ConstraintPredicate<>(s -> !s.isBlank(), message));
        return this;
    }

    public StringConstraintPredicate notBlank(Exception exception) {
        this.constraints.add(new ConstraintPredicate<>(s -> !s.isBlank(), exception));
        return this;
    }

    public StringConstraintPredicate notEmpty() {
        this.constraints.add(new ConstraintPredicate<>(s -> !s.isEmpty()));
        return this;
    }

    public StringConstraintPredicate notEmpty(String message) {
        this.constraints.add(new ConstraintPredicate<>(s -> !s.isEmpty(), message));
        return this;
    }

    public StringConstraintPredicate notEmpty(Exception exception) {
        this.constraints.add(new ConstraintPredicate<>(s -> !s.isEmpty(), exception));
        return this;
    }

    public StringConstraintPredicate length(int length) {
        this.constraints.add(new ConstraintPredicate<>(s -> s.length() == length));
        return this;
    }

    public StringConstraintPredicate length(int length, String message) {
        this.constraints.add(new ConstraintPredicate<>(s -> s.length() == length, message));
        return this;
    }

    public StringConstraintPredicate length(int length, Exception exception) {
        this.constraints.add(new ConstraintPredicate<>(s -> s.length() == length, exception));
        return this;
    }

    public StringConstraintPredicate minLength(int length) {
        this.constraints.add(new ConstraintPredicate<>(s -> s.length() >= length));
        return this;
    }

    public StringConstraintPredicate minLength(int length, String message) {
        this.constraints.add(new ConstraintPredicate<>(s -> s.length() >= length, message));
        return this;
    }

    public StringConstraintPredicate minLength(int length, Exception exception) {
        this.constraints.add(new ConstraintPredicate<>(s -> s.length() >= length, exception));
        return this;
    }

    public StringConstraintPredicate maxLength(int length) {
        this.constraints.add(new ConstraintPredicate<>(s -> s.length() <= length));
        return this;
    }

    public StringConstraintPredicate maxLength(int length, String message) {
        this.constraints.add(new ConstraintPredicate<>(s -> s.length() <= length, message));
        return this;
    }

    public StringConstraintPredicate maxLength(int length, Exception exception) {
        this.constraints.add(new ConstraintPredicate<>(s -> s.length() <= length, exception));
        return this;
    }

    public StringConstraintPredicate matches(String regex) {
        this.constraints.add(new ConstraintPredicate<>(s -> s.matches(regex)));
        return this;
    }

    public StringConstraintPredicate matches(String regex, String message) {
        this.constraints.add(new ConstraintPredicate<>(s -> s.matches(regex), message));
        return this;
    }

    public StringConstraintPredicate matches(String regex, Exception exception) {
        this.constraints.add(new ConstraintPredicate<>(s -> s.matches(regex), exception));
        return this;
    }

    public StringConstraintPredicate contains(String sequence) {
        this.constraints.add(new ConstraintPredicate<>(s -> s.contains(sequence)));
        return this;
    }

    public StringConstraintPredicate contains(String sequence, String message) {
        this.constraints.add(new ConstraintPredicate<>(s -> s.contains(sequence), message));
        return this;
    }

    public StringConstraintPredicate contains(String sequence, Exception exception) {
        this.constraints.add(new ConstraintPredicate<>(s -> s.contains(sequence), exception));
        return this;
    }

    public StringConstraintPredicate startsWith(String sequence) {
        this.constraints.add(new ConstraintPredicate<>(s -> s.startsWith(sequence)));
        return this;
    }

    public StringConstraintPredicate startsWith(String sequence, String message) {
        this.constraints.add(new ConstraintPredicate<>(s -> s.startsWith(sequence), message));
        return this;
    }

    public StringConstraintPredicate startsWith(String sequence, Exception exception) {
        this.constraints.add(new ConstraintPredicate<>(s -> s.startsWith(sequence), exception));
        return this;
    }

    public StringConstraintPredicate endsWith(String sequence) {
        this.constraints.add(new ConstraintPredicate<>(s -> s.endsWith(sequence)));
        return this;
    }

    public StringConstraintPredicate endsWith(String sequence, String message) {
        this.constraints.add(new ConstraintPredicate<>(s -> s.endsWith(sequence), message));
        return this;
    }

    public StringConstraintPredicate endsWith(String sequence, Exception exception) {
        this.constraints.add(new ConstraintPredicate<>(s -> s.endsWith(sequence), exception));
        return this;
    }

    public StringConstraintPredicate isLowerCase() {
        this.constraints.add(new ConstraintPredicate<>(s -> s.equals(s.toLowerCase())));
        return this;
    }

    public StringConstraintPredicate isLowerCase(String message) {
        this.constraints.add(new ConstraintPredicate<>(s -> s.equals(s.toLowerCase()), message));
        return this;
    }

    public StringConstraintPredicate isLowerCase(Exception exception) {
        this.constraints.add(new ConstraintPredicate<>(s -> s.equals(s.toLowerCase()), exception));
        return this;
    }

    public StringConstraintPredicate isUpperCase() {
        this.constraints.add(new ConstraintPredicate<>(s -> s.equals(s.toUpperCase())));
        return this;
    }

    public StringConstraintPredicate isUpperCase(String message) {
        this.constraints.add(new ConstraintPredicate<>(s -> s.equals(s.toUpperCase()), message));
        return this;
    }

    public StringConstraintPredicate isUpperCase(Exception exception) {
        this.constraints.add(new ConstraintPredicate<>(s -> s.equals(s.toUpperCase()), exception));
        return this;
    }

    public StringConstraintPredicate email() {
        this.constraints.add(new ConstraintPredicate<>(s -> s.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")));
        return this;
    }

}