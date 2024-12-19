package org.pay_my_buddy.modules.user.shared;

import org.pay_my_buddy.core.framework.domain.value_object.EntityId;

public record UserId(String value) implements EntityId {

    private final static String PREFIX = "USER_";

    public UserId {
        EntityId.validate(value, PREFIX);
    }

    public UserId() {
        this(PREFIX + EntityId.generateId());
    }

    public UserId(EntityId value) {
        this(value.value());
    }
}