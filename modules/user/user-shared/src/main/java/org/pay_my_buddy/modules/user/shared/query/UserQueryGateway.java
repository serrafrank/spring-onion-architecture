package org.pay_my_buddy.modules.user.shared.query;

import org.pay_my_buddy.core.framework.domain.Gateway;
import org.pay_my_buddy.modules.user.shared.UserEntityProjection;

import java.util.Optional;

public interface UserQueryGateway extends Gateway {

    Optional<UserEntityProjection> handle(FindUserByEmailQuery query);

    Optional<UserEntityProjection> handle(FindUserByIdQuery query);


}
