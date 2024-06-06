package org.pay_my_buddy.application.use_case.user.command.add_friend;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.pay_my_buddy.application.common.api.AbstractEvent;
import org.pay_my_buddy.application.common.api.Request;
import org.pay_my_buddy.entity.user.UserId;

@Getter
@EqualsAndHashCode(callSuper = true)
public class FriendAddedEvent extends AbstractEvent {

    private final UserId userId;
    private final UserId friendId;

    private FriendAddedEvent(Request trigger, UserId userId, UserId friendId) {
        super(trigger);
        this.userId = userId;
        this.friendId = friendId;
    }

    private FriendAddedEvent(FriendAddedEvent eventObject) {
        super(eventObject);
        this.userId = eventObject.userId();
        this.friendId = eventObject.friendId();
    }

    public static FriendAddedEvent of(Request trigger, UserId userId, UserId friendId) {
        return new FriendAddedEvent(trigger, userId, friendId);
    }

    public static FriendAddedEvent of(FriendAddedEvent eventObject) {
        return new FriendAddedEvent(eventObject);
    }
}
