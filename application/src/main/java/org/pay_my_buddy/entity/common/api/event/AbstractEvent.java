package org.pay_my_buddy.entity.common.api.event;

import org.pay_my_buddy.entity.common.api.AbstractRequest;

public class AbstractEvent extends AbstractRequest implements Event {

    public AbstractEvent() {
        super();
    }

    public AbstractEvent(Event eventObject) {
        super(eventObject);
    }
}
