package org.pay_my_buddy.core.framework.domain.message;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public interface Event extends Message {
    EventId eventId();
}
