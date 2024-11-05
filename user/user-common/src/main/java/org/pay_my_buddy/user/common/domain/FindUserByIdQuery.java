package org.pay_my_buddy.user.common.domain;

import org.pay_my_buddy.shared.common.domain.api.query.Query;
import org.pay_my_buddy.shared.common.domain.entity.EntityId;

import java.util.Optional;

public record FindUserByIdQuery(EntityId id) implements Query<Optional<UserEntityProjection>> {
}
