package org.pay_my_buddy.core.framework.domain.message;

import org.pay_my_buddy.core.framework.domain.validator.Validate;
import org.pay_my_buddy.core.framework.domain.value_object.EntityId;

public record EventId(String value) implements EntityId {

    private final static String PREFIX = "EVENT_";

    public EventId {
        validate(value);
    }

    public EventId() {
        this(PREFIX + EntityId.generateId());
    }

    public EventId(EntityId value) {
        this(value.value());
    }

    @Override
    public String prefix() {
        return PREFIX;
    }
}
