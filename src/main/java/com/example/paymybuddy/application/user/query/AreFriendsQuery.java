package com.example.paymybuddy.application.user.query;

import com.example.paymybuddy.application.Query;
import com.example.paymybuddy.core.common.entity.id.Id;
import com.example.paymybuddy.application.user.domain.UserId;
import lombok.NonNull;

public record AreFriendsQuery(@NonNull UserId userId, @NonNull UserId friendId) implements Query {

        public AreFriendsQuery(Id userId, Id friendId) {
            this(new UserId(userId), new UserId(friendId));
        }
}
