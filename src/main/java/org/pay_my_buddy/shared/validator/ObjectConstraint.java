package org.pay_my_buddy.shared.validator;

import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.Supplier;
import org.pay_my_buddy.shared.exception.BadArgumentException;

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

    public ObjectConstraint<V> predicate(Predicate<V> predicate) {
        validate(predicate);
        return this;
    }

    public ObjectConstraint<V> predicate(Predicate<V> predicate, String message) {
        validate(predicate, message);
        return this;
    }

    public ObjectConstraint<V> predicate(Predicate<V> predicate, Supplier<Throwable> thowableSupplier) {
        validate(predicate, thowableSupplier);
        return this;
    }

    public ObjectConstraint<V> isNotNull() {
        validate(Objects::nonNull);
        return this;
    }

    public ObjectConstraint<V> isNotNull(String message) {
        validate(() -> isNotNull(), message);
        return this;
    }

    public ObjectConstraint<V> isNotNull(Supplier<Throwable> thowableSupplier) {
        validate(() -> isNotNull(), thowableSupplier);
        return this;
    }

    protected void validate(Predicate<V> constraint) {
        validate(constraint, DEFAULT_THROWABLE_SUPPLIER);
    }

    protected void validate(Predicate<V> constraint, String message) {
        validate(constraint, () -> new BadArgumentException(message));
    }

    protected void validate(Supplier<ObjectConstraint<?>> constraint, String message) {
        validate(constraint, () -> new BadArgumentException(message));
    }

    protected void validate(Supplier<ObjectConstraint<?>> constraint, Supplier<Throwable> thowableSupplier) {
        try {
            constraint.get();
        } catch (Throwable e) {
            sneakyThrow(thowableSupplier.get());
        }
    }

    protected void validate(Predicate<V> constraint, Supplier<Throwable> thowableSupplier) {
        if (!constraint.test(value)) {
            sneakyThrow(thowableSupplier.get());
        }
    }


}
