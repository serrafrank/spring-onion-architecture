package org.pay_my_buddy.modules.money_account.shared;

import org.pay_my_buddy.core.framework.domain.value_object.EntityId;

public record MoneyAccountId(String value)  implements EntityId {

    private final static String PREFIX = "MONEY_ACCOUNT_";

    public MoneyAccountId {
        validate(value);
    }

    public MoneyAccountId() {
        this(PREFIX + EntityId.generateId());
    }

    public MoneyAccountId(EntityId value) {
        this(value.value());
    }

    @Override
    public String prefix() {
        return PREFIX;
    }
}
