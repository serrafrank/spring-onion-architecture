package org.pay_my_buddy.entity.user.api;

import org.pay_my_buddy.entity.commun.api.query.Query;
import org.pay_my_buddy.entity.commun.entity.Id;
import org.pay_my_buddy.entity.user.UserId;

public record AreFriendsQuery(UserId userId, UserId friendId) implements Query<Boolean> {

    public AreFriendsQuery {
        if (userId == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        if (friendId == null) {
            throw new IllegalArgumentException("Friend cannot be null");
        }

        if (userId.equals(friendId)) {
            throw new IllegalArgumentException("User and friend cannot be the same");
        }
    }

    public AreFriendsQuery(Id userId, Id friendId) {
        this(UserId.of(userId), UserId.of(friendId));
    }
}
