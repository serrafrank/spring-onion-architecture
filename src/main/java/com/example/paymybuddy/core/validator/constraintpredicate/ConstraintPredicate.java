package com.example.paymybuddy.core.validator.constraintpredicate;

import lombok.Getter;
import lombok.SneakyThrows;

import java.util.function.Predicate;

public class ConstraintPredicate<V> {

    private final Predicate<V> predicate;

    @Getter
    private final Exception exception;

    public ConstraintPredicate(Predicate<V> predicate, String message) {
        this.predicate = predicate;
        this.exception = new IllegalArgumentException(message);
    }

    public ConstraintPredicate(Predicate<V> predicate, Exception exception) {
        this.predicate = predicate;
        this.exception = exception;
    }

    public ConstraintPredicate(Predicate<V> predicate) {
        this.predicate = predicate;
        this.exception = new IllegalArgumentException("Invalid value");
    }

    public void validate(V value) {
        if (!predicate.test(value)) {
            sneakyThrow(exception);
        }
    }

    private static <E extends Exception> void sneakyThrow(Exception e) throws E {
        throw (E) e;
    }
}
