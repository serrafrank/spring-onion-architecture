package org.pay_my_buddy.modules.user.shared.command;

import org.pay_my_buddy.core.framework.domain.message.Event;
import org.pay_my_buddy.core.framework.domain.message.EventId;
import org.pay_my_buddy.core.framework.domain.validator.Validate;
import org.pay_my_buddy.modules.user.shared.UserId;


public record UserCreatedEvent(
        EventId eventId,

        UserId id,
        String firstname,
        String lastname,
        String email,
        String password
) implements Event {


    public UserCreatedEvent {
        Validate.checkIf(id).isNotNull("User id is required");
        Validate.checkIf(firstname).notBlank("firstname can not be blank");
        Validate.checkIf(lastname).notBlank("lastname can not be blank");
        Validate.checkIf(email)
                .notBlank("email can not be blank")
                .email();
        Validate.checkIf(password).notBlank("password is required");
    }

    public UserCreatedEvent(UserId id, String firstname, String lastname, String email, String password) {
        this(new EventId(), id, firstname, lastname, email, password);
    }
}
