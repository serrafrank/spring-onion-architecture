package org.pay_my_buddy.shared.exchange.user;

import org.pay_my_buddy.shared.EntityId;

import java.util.UUID;

public class UserId extends EntityId {

    private UserId(EntityId id) {
        super(id);
    }

    public static UserId createRandomUnique() {
        return new UserId(EntityId.createRandomUnique());
    }

    public static UserId of(String id) {
        return new UserId(EntityId.of(id));
    }

    public static UserId of(EntityId id) {
        return new UserId(EntityId.of(id));
    }

    public static UserId of(UUID id) {
        return new UserId(EntityId.of(id));
    }
}
