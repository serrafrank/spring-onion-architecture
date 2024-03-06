package com.example.paymybuddy.application.user.query;

import com.example.paymybuddy.application.shared.entity.id.Id;
import com.example.paymybuddy.application.shared.message_handler.Query;
import com.example.paymybuddy.application.user.domain.UserId;
import lombok.NonNull;

public record AreFriendsQuery(@NonNull UserId userId, @NonNull UserId friendId) implements Query<Boolean> {

    public AreFriendsQuery(Id userId, Id friendId) {
        this(new UserId(userId), new UserId(friendId));
    }
}
