package org.pay_my_buddy.shared.domain.validator;

import org.pay_my_buddy.shared.domain.exception.BadArgumentException;

import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class ObjectConstraint<V> {

    private static final String DEFAULT_MESSAGE = "Value is not valid";
    private static final Supplier<Throwable> DEFAULT_THROWABLE_SUPPLIER = () -> new BadArgumentException(DEFAULT_MESSAGE);


    protected final V value;


    public ObjectConstraint(V value) {
        this.value = value;
    }

    private static <THROWABLE extends Throwable> void sneakyThrow(Throwable e) throws THROWABLE {
        throw (THROWABLE) e;
    }

    public ObjectConstraint<V> notNull() {
        validate(Objects::nonNull);
        return this;
    }

    public ObjectConstraint<V> notNull(String message) {
        validate(Objects::nonNull, message);
        return this;
    }

    public ObjectConstraint<V> notNull(Supplier<Throwable> thowableSupplier) {
        validate(Objects::nonNull, thowableSupplier);
        return this;
    }

    protected void validate(Predicate<V> constraint) {
        validate(constraint, DEFAULT_THROWABLE_SUPPLIER);
    }

    protected void validate(Predicate<V> constraint, String message) {
        validate(constraint, () -> new BadArgumentException(message));
    }

    protected void validate(Predicate<V> constraint, Supplier<Throwable> thowableSupplier) {
        if (!constraint.test(value)) {
            sneakyThrow(thowableSupplier.get());
        }
    }

}
