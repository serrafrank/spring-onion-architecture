package com.example.paymybuddy.application.user.query;

import com.example.paymybuddy.application.shared.use_case.query.Query;
import com.example.paymybuddy.application.shared.entity.id.Id;
import com.example.paymybuddy.application.user.domain.UserId;
import lombok.NonNull;

public record FindUserByIdQuery(@NonNull UserId id) implements Query {

            public FindUserByIdQuery(Id id) {
                this(new UserId(id));
            }
}
