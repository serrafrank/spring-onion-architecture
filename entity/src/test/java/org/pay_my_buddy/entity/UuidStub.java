package org.pay_my_buddy.entity;

import org.pay_my_buddy.entity.common.entity.AbstractId;

import java.util.UUID;

public class UuidStub extends AbstractId<UUID> {


    @Override
    public UUID generateUniqueId() {
        return  UUID.fromString("00000000-0000-0000-0000-000000000000");
    }
}
