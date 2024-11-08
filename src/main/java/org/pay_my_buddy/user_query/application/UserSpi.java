package org.pay_my_buddy.user_query.application;

import org.pay_my_buddy.shared.exchange.user.UserEntityProjection;
import org.pay_my_buddy.shared.exchange.user.UserId;

import java.util.Optional;

public interface UserSpi {

	Optional<UserEntityProjection> findUserByEmail(String email);
	Optional<UserEntityProjection> findUserById(UserId id);
}
