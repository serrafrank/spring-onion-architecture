package org.pay_my_buddy.modules.user.shared.query;

import org.pay_my_buddy.core.framework.domain.message.Query;
import org.pay_my_buddy.core.framework.domain.validator.Validate;
import org.pay_my_buddy.core.framework.domain.value_object.EntityId;
import org.pay_my_buddy.modules.user.shared.UserId;

public record UserIsActiveQuery(UserId id) implements Query {

    public UserIsActiveQuery {
        Validate.checkIf(id).isNotNull();
    }

    public UserIsActiveQuery(EntityId id) {
        this(new UserId(id));
    }
}
