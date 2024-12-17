package org.pay_my_buddy.modules.user.shared.query;

import org.pay_my_buddy.core.framework.domain.message.Query;
import org.pay_my_buddy.modules.user.shared.UserId;

public record FindUserByIdQuery(UserId id) implements Query {
}
