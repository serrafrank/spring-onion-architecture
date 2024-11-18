package org.pay_my_buddy.user_query.application;

import java.util.Optional;
import org.pay_my_buddy.shared.exchange.user.UserEntityProjection;
import org.pay_my_buddy.shared.exchange.user.UserId;

public interface UserSpi {

	Optional<UserEntityProjection> findUserByEmail(String email);

	Optional<UserEntityProjection> findUserById(UserId id);
}
