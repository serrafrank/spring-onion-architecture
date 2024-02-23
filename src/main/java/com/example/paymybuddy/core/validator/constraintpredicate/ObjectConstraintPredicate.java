package com.example.paymybuddy.core.validator.constraintpredicate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ObjectConstraintPredicate<V> {

    protected final V value;

    protected List<ConstraintPredicate<V>> constraints = new ArrayList<>();

    public ObjectConstraintPredicate(V value) {
        this.value = value;
    }

    public ObjectConstraintPredicate<V> notNull() {
        this.constraints.add(new ConstraintPredicate<>(Objects::nonNull));
        return this;
    }

    public ObjectConstraintPredicate<V> notNull(String message) {
        this.constraints.add(new ConstraintPredicate<>(Objects::nonNull, message));
        return this;
    }

    public ObjectConstraintPredicate<V> notNull(Exception exception) {
        this.constraints.add(new ConstraintPredicate<>(Objects::nonNull, exception));
        return this;
    }

    public void validate() {
        constraints.forEach(c -> c.validate(value));
    }

}
