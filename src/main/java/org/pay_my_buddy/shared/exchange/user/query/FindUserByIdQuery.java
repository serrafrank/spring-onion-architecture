package org.pay_my_buddy.shared.exchange.user.query;

import java.util.Optional;
import org.pay_my_buddy.api_query.Query;
import org.pay_my_buddy.shared.exchange.user.UserEntityProjection;
import org.pay_my_buddy.shared.exchange.user.UserId;

public record FindUserByIdQuery(UserId id) implements Query<Optional<UserEntityProjection>> {
}
