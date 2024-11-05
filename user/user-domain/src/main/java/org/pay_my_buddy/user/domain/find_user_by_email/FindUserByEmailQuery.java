package org.pay_my_buddy.user.domain.find_user_by_email;

import org.pay_my_buddy.shared.domain.api.query.Query;
import org.pay_my_buddy.user.domain.UserEntityProjection;

import java.util.Optional;

public record FindUserByEmailQuery(String email) implements Query<Optional<UserEntityProjection>> {
}
