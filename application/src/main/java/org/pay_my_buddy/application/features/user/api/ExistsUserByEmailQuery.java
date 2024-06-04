package org.pay_my_buddy.application.features.user.api;

import lombok.EqualsAndHashCode;
import lombok.Value;
import org.pay_my_buddy.application.common.api.query.AbstractQuery;
import org.pay_my_buddy.entity.user.Email;

@Value
@EqualsAndHashCode(callSuper = true)
public class ExistsUserByEmailQuery extends AbstractQuery<Boolean> {
    Email email;

    private ExistsUserByEmailQuery(Email email) {
        this.email = email;
    }

    public static ExistsUserByEmailQuery of(Email email) {
        return new ExistsUserByEmailQuery(email);
    }

}
