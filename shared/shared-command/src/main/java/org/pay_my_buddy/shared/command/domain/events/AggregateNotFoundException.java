package org.pay_my_buddy.shared.command.domain.events;

import org.pay_my_buddy.shared.common.domain.exception.NotFoundException;

public class AggregateNotFoundException extends NotFoundException {
    public AggregateNotFoundException(String message) {
        super(message);
    }
}
