package org.pay_my_buddy.core.command.domain.event_storage;

import org.pay_my_buddy.core.framework.domain.exception.NotFoundException;

public class AggregateNotFoundException extends NotFoundException {
    public AggregateNotFoundException(String message) {
        super(message);
    }
}
