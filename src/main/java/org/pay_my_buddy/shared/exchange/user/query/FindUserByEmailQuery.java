package org.pay_my_buddy.shared.exchange.user.query;

import org.pay_my_buddy.api_query.Query;
import org.pay_my_buddy.shared.exchange.user.UserEntityProjection;

import java.util.Optional;

public record FindUserByEmailQuery(String email) implements Query<Optional<UserEntityProjection>> {
}
