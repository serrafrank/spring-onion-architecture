package org.pay_my_buddy.entity;


import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public abstract class AbstractValueObject<T> {

    protected T value;

    @Override
    public String toString() {
        return value.toString();
    }
}
