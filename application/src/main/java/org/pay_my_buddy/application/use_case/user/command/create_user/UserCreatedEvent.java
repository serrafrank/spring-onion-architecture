package org.pay_my_buddy.application.use_case.user.command.create_user;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.pay_my_buddy.application.common.api.AbstractEvent;
import org.pay_my_buddy.application.common.api.Event;
import org.pay_my_buddy.application.common.api.Request;
import org.pay_my_buddy.entity.user.User;


@Getter
@EqualsAndHashCode(callSuper = true)
public class UserCreatedEvent extends AbstractEvent {

    User user;

    protected UserCreatedEvent(Event eventObject) {
        super(eventObject);
    }

    protected UserCreatedEvent(Request trigger, User user) {
        super(trigger);
        this.user = user;
    }

    public static UserCreatedEvent of(Event eventObject) {
        return new UserCreatedEvent(eventObject);
    }

    public static UserCreatedEvent of(Request trigger, User user) {
        return new UserCreatedEvent(trigger, user);
    }
}
