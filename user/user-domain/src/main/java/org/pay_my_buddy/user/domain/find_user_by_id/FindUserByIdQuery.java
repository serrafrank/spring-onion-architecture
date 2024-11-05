package org.pay_my_buddy.user.domain.find_user_by_id;

import org.pay_my_buddy.shared.domain.api.query.Query;
import org.pay_my_buddy.shared.domain.entity.EntityId;
import org.pay_my_buddy.user.domain.UserEntityProjection;

import java.util.Optional;

public record FindUserByIdQuery(EntityId id) implements Query<Optional<UserEntityProjection>> {
}
