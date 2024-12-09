package org.pay_my_buddy.shared.exchange.user.command;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.pay_my_buddy.api_command.Event;
import org.pay_my_buddy.api_command.EventId;
import org.pay_my_buddy.shared.Constraint;
import org.pay_my_buddy.shared.exchange.user.UserId;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonSerialize
public record UserCreatedEvent(
        EventId eventId,

        UserId id,
        String firstname,
        String lastname,
        String email,
        String password
) implements Event {

    @JsonCreator
    public UserCreatedEvent {
        Constraint.checkIf(id).isNotNull("User id is required");
        Constraint.checkIf(firstname).notBlank("firstname can not be blank");
        Constraint.checkIf(lastname).notBlank("lastname can not be blank");
        Constraint.checkIf(email)
                .notBlank("email can not be blank")
                .email();
        Constraint.checkIf(password).notBlank("password is required");
    }

    public UserCreatedEvent(UserId id, String firstname, String lastname, String email, String password) {
        this(new EventId(), id, firstname, lastname, email, password);
    }
}
