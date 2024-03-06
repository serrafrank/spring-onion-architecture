package com.example.paymybuddy.application.user.query;

import com.example.paymybuddy.application.shared.entity.id.Id;
import com.example.paymybuddy.application.shared.message_handler.Query;
import com.example.paymybuddy.application.user.domain.UserId;
import lombok.NonNull;

public record ExistsUserByIdQuery(@NonNull UserId id) implements Query<Boolean> {

    public ExistsUserByIdQuery(Id id) {
        this(new UserId(id));
    }
}
