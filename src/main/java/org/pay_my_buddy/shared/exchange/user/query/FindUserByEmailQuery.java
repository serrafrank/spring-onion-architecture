package org.pay_my_buddy.shared.exchange.user.query;

import java.util.Optional;
import org.pay_my_buddy.api_query.Query;
import org.pay_my_buddy.shared.exchange.user.UserEntityProjection;

public record FindUserByEmailQuery(String email) implements Query<Optional<UserEntityProjection>> {
}
