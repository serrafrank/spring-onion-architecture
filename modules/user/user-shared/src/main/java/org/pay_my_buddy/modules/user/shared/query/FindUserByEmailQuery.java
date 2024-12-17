package org.pay_my_buddy.modules.user.shared.query;

import org.pay_my_buddy.core.framework.domain.message.Query;

public record FindUserByEmailQuery(String email) implements Query {
}
