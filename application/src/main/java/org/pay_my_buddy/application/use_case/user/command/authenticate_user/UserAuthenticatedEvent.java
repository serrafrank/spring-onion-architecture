package org.pay_my_buddy.application.use_case.user.command.authenticate_user;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.pay_my_buddy.application.common.api.AbstractEvent;
import org.pay_my_buddy.application.common.api.Event;
import org.pay_my_buddy.application.common.api.Request;
import org.pay_my_buddy.entity.user.User;


@Getter
@EqualsAndHashCode(callSuper = true)
public class UserAuthenticatedEvent extends AbstractEvent {

    User user;

    protected UserAuthenticatedEvent(Event eventObject) {
        super(eventObject);
    }

    protected UserAuthenticatedEvent(Request trigger, User user) {
        super(trigger);
        this.user = user;
    }

    public static UserAuthenticatedEvent of(Event eventObject) {
        return new UserAuthenticatedEvent(eventObject);
    }

    public static UserAuthenticatedEvent of(Request trigger, User user) {
        return new UserAuthenticatedEvent(trigger, user);
    }
}
