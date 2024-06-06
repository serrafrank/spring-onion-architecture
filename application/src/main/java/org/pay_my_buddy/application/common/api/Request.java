package org.pay_my_buddy.application.common.api;

import org.pay_my_buddy.entity.Id;

import java.time.LocalDateTime;

public interface Request extends Cloneable {

    Metadata metadata();


    interface Metadata extends Cloneable {
        Id requestId();

        LocalDateTime occurredOn();

        String name();

        String type();

        Request trigger();
    }

}
