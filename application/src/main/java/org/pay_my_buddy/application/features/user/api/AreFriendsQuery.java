package org.pay_my_buddy.application.features.user.api;

import lombok.EqualsAndHashCode;
import lombok.Value;
import org.pay_my_buddy.application.common.api.query.AbstractQuery;
import org.pay_my_buddy.entity.Id;
import org.pay_my_buddy.entity.user.UserId;

@Value
@EqualsAndHashCode(callSuper = true)
public class AreFriendsQuery extends AbstractQuery<Boolean> {
    UserId userId;
    UserId friendId;


    private AreFriendsQuery(UserId userId, UserId friendId) {
        if (userId == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        if (friendId == null) {
            throw new IllegalArgumentException("Friend cannot be null");
        }

        if (userId.equals(friendId)) {
            throw new IllegalArgumentException("User and friend cannot be the same");
        }
        this.userId = userId;
        this.friendId = friendId;
    }

    public static AreFriendsQuery of(UserId userId, UserId friendId) {
        return new AreFriendsQuery(userId, friendId);
    }

    public AreFriendsQuery(Id userId, Id friendId) {
        this(UserId.of(userId), UserId.of(friendId));
    }
}
