package com.example.paymybuddy.application.user.query;

import com.example.paymybuddy.core.common.entity.id.Id;
import com.example.paymybuddy.core.user.valueobject.UserId;
import lombok.NonNull;

public record AreFriendsQuery(@NonNull UserId userId, @NonNull UserId friendId) {

        public AreFriendsQuery(Id userId, Id friendId) {
            this(new UserId(userId), new UserId(friendId));
        }
}
