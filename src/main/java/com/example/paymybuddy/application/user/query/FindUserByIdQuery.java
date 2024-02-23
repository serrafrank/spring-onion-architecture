package com.example.paymybuddy.application.user.query;

import com.example.paymybuddy.core.common.entity.id.Id;
import com.example.paymybuddy.core.user.valueobject.UserId;
import lombok.NonNull;

public record FindUserByIdQuery(@NonNull UserId id) {

            public FindUserByIdQuery(Id id) {
                this(new UserId(id));
            }
}
