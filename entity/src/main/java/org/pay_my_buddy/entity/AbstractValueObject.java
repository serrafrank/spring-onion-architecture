package org.pay_my_buddy.entity;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@EqualsAndHashCode
@Accessors(fluent = true)
public abstract class AbstractValueObject<T> {

    protected T value;

    @Override
    public String toString() {
        return value.toString();
    }
}
