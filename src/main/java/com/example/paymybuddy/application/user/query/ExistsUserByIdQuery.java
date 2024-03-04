package com.example.paymybuddy.application.user.query;

import com.example.paymybuddy.application.Query;
import com.example.paymybuddy.core.common.entity.id.Id;
import com.example.paymybuddy.application.user.domain.UserId;
import lombok.NonNull;

public record ExistsUserByIdQuery(@NonNull UserId id)  implements Query {

        public ExistsUserByIdQuery(Id id) {
            this(new UserId(id));
        }
}
