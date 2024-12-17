package org.pay_my_buddy.modules.user.query.application;

import org.pay_my_buddy.modules.user.shared.UserEntityProjection;
import org.pay_my_buddy.modules.user.shared.UserId;

import java.util.Optional;

public interface UserSpi {

    Optional<UserEntityProjection> findUserByEmail(String email);

    Optional<UserEntityProjection> findUserById(UserId id);
}
