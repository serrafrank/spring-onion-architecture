package org.pay_my_buddy.modules.user.query.application;

import java.util.Optional;
import org.pay_my_buddy.modules.user.shared.UserEntityProjection;
import org.pay_my_buddy.modules.user.shared.UserId;

public interface UserSpi {

    Optional<UserEntityProjection> findUserByEmail(String email);

    Optional<UserEntityProjection> findUserById(UserId id);
}
