package com.example.paymybuddy.application.user.query;

import com.example.paymybuddy.core.common.entity.id.Id;
import com.example.paymybuddy.core.user.valueobject.UserId;
import lombok.NonNull;

public record ExistsUserByIdQuery(@NonNull UserId id) {

        public ExistsUserByIdQuery(Id id) {
            this(new UserId(id));
        }
}
