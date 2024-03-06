package com.example.paymybuddy.application.user.query;

import com.example.paymybuddy.application.shared.entity.id.Id;
import com.example.paymybuddy.application.shared.message_handler.Query;
import com.example.paymybuddy.application.user.domain.UserAggregate;
import com.example.paymybuddy.application.user.domain.UserId;
import lombok.NonNull;

public record FindUserByIdQuery(@NonNull UserId id) implements Query<UserAggregate> {

    public FindUserByIdQuery(Id id) {
        this(new UserId(id));
    }
}
