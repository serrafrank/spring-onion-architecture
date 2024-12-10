package org.pay_my_buddy.core.framework.domain.validator;

import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.Supplier;
import org.pay_my_buddy.core.framework.domain.exception.BadArgumentException;
import org.pay_my_buddy.core.framework.domain.exception.BusinessException;

public class ObjectValidator<V> {

    private static final String DEFAULT_MESSAGE = "Value is not valid";
    private static final Supplier<Throwable> DEFAULT_THROWABLE_SUPPLIER = () -> BusinessException.wrap(new BadArgumentException(DEFAULT_MESSAGE));


    protected final V value;


    public ObjectValidator(V value) {
        this.value = value;
    }

    private static <THROWABLE extends Throwable> void sneakyThrow(Throwable e) throws THROWABLE {
        throw (THROWABLE) e;
    }

    public ObjectValidator<V> predicate(Predicate<V> predicate) {
        validate(predicate);
        return this;
    }

    public ObjectValidator<V> predicate(Predicate<V> predicate, String message) {
        validate(predicate, message);
        return this;
    }

    public ObjectValidator<V> predicate(Predicate<V> predicate, Supplier<Throwable> thowableSupplier) {
        validate(predicate, thowableSupplier);
        return this;
    }

    public ObjectValidator<V> isNotNull() {
        validate(Objects::nonNull);
        return this;
    }

    public ObjectValidator<V> isNotNull(String message) {
        validate(() -> isNotNull(), message);
        return this;
    }

    public ObjectValidator<V> isNotNull(Supplier<Throwable> thowableSupplier) {
        validate(() -> isNotNull(), thowableSupplier);
        return this;
    }

    protected void validate(Predicate<V> constraint) {
        validate(constraint, DEFAULT_THROWABLE_SUPPLIER);
    }

    protected void validate(Predicate<V> constraint, String message) {
        validate(constraint, () -> BusinessException.wrap(new BadArgumentException(message)));
    }

    protected void validate(Supplier<ObjectValidator<?>> constraint, String message) {
        validate(constraint, () -> BusinessException.wrap(new BadArgumentException(message)));
    }

    protected void validate(Supplier<ObjectValidator<?>> constraint, Supplier<Throwable> thowableSupplier) {
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
