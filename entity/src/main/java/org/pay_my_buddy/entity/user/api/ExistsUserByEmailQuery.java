package org.pay_my_buddy.entity.user.api;

import org.pay_my_buddy.entity.commun.api.query.Query;

public record ExistsUserByEmailQuery(String email) implements Query<Boolean> {

    public ExistsUserByEmailQuery {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
    }
}
