package org.pay_my_buddy.shared.domain.api.events;

import org.pay_my_buddy.shared.domain.exception.NotFoundException;

public class AggregateNotFoundException extends NotFoundException {
    public AggregateNotFoundException(String message) {
        super(message);
    }
}
