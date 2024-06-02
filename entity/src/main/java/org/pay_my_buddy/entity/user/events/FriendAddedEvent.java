package org.pay_my_buddy.entity.user.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.pay_my_buddy.entity.common.api.event.Event;
import org.pay_my_buddy.entity.common.api.event.EventObject;
import org.pay_my_buddy.entity.user.UserId;

@Getter
@Accessors(fluent = true)
public class FriendAddedEvent extends EventObject {

        private final UserId userId;
        private final UserId friendId;

        private FriendAddedEvent(UserId userId, UserId friendId) {
            super();
            this.userId = userId;
            this.friendId = friendId;
        }

    public static Event of(UserId userId, UserId friendId) {
        return new FriendAddedEvent(userId, friendId);
    }
}
