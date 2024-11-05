package org.pay_my_buddy.user.common.domain;

import org.pay_my_buddy.shared.common.domain.api.query.Query;

import java.util.Optional;

public record FindUserByEmailQuery(String email) implements Query<Optional<UserEntityProjection>> {
}
