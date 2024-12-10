package org.pay_my_buddy.modules.user.shared.query;

import java.util.Optional;
import org.pay_my_buddy.core.framework.domain.message.Query;
import org.pay_my_buddy.modules.user.shared.UserEntityProjection;

public record FindUserByEmailQuery(String email) implements Query<Optional<UserEntityProjection>> {
}
