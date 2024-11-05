package org.pay_my_buddy.user.query.domain;

import org.pay_my_buddy.shared.common.domain.entity.EntityId;
import org.pay_my_buddy.user.common.domain.UserEntityProjection;

import java.util.Optional;

public interface UserSpi {

	Optional<UserEntityProjection> findUserByEmail(String email);
	Optional<UserEntityProjection> findUserById(EntityId id);
}
