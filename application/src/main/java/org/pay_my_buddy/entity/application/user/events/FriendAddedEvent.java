package org.pay_my_buddy.entity.application.user.events;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.pay_my_buddy.entity.common.api.event.AbstractEvent;
import org.pay_my_buddy.entity.user.UserId;

@Getter
@Accessors(fluent = true)
@EqualsAndHashCode(callSuper = true)
public class FriendAddedEvent extends AbstractEvent {

    private final UserId userId;
    private final UserId friendId;

    private FriendAddedEvent(UserId userId, UserId friendId) {
        super();
        this.userId = userId;
        this.friendId = friendId;
    }

    private FriendAddedEvent(FriendAddedEvent eventObject) {
        super(eventObject);
        this.userId = eventObject.userId();
        this.friendId = eventObject.friendId();
    }

    public static FriendAddedEvent of(UserId userId, UserId friendId) {
        return new FriendAddedEvent(userId, friendId);
    }

    public static FriendAddedEvent of(FriendAddedEvent eventObject) {
        return new FriendAddedEvent(eventObject);
    }
}
