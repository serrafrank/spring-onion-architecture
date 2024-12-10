package org.pay_my_buddy.core.framework.domain.message;

import org.pay_my_buddy.core.framework.domain.value_object.EntityId;
import org.pay_my_buddy.core.framework.domain.validator.Validate;

public record EventId(String value) implements EntityId {

    private final static String PREFIX = "EVENT_";

    public EventId {
        Validate.checkIf(value).isNotNull("EventId is required")
                .predicate(v -> EntityId.isValid(v, PREFIX), "EventId not valid with value = " + value);
    }

    public EventId() {
        this(PREFIX + EntityId.generateId());
    }

    public EventId(EntityId value) {
        this(value.value());
    }
}
