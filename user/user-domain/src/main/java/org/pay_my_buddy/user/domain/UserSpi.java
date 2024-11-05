package org.pay_my_buddy.user.domain;

import org.pay_my_buddy.shared.domain.entity.EntityId;

import java.util.Optional;

public interface UserSpi {

	Optional<UserEntityProjection> findUserByEmail(String email);
	Optional<UserEntityProjection> findUserById(EntityId id);
}
