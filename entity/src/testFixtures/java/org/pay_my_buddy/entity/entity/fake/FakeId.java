package org.pay_my_buddy.entity.fake;


import org.pay_my_buddy.entity.AbstractId;

import java.util.UUID;

public class FakeId extends AbstractId<UUID> {

    private FakeId() {
    }

    public static FakeId of() {
        return new FakeId();
    }

    @Override
    public UUID generateUniqueId() {
        return UUID.fromString("00000000-0000-0000-0000-000000000000");
    }


}
