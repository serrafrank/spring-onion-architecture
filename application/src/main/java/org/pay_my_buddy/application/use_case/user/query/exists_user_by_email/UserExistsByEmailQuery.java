package org.pay_my_buddy.application.use_case.user.query.exists_user_by_email;

import lombok.EqualsAndHashCode;
import lombok.Value;
import org.pay_my_buddy.application.common.api.AbstractQuery;
import org.pay_my_buddy.entity.user.Email;

@Value
@EqualsAndHashCode(callSuper = true)
public class UserExistsByEmailQuery extends AbstractQuery<Boolean> {
    Email email;

    private UserExistsByEmailQuery(Email email) {
        this.email = email;
    }

    public static UserExistsByEmailQuery of(Email email) {
        return new UserExistsByEmailQuery(email);
    }

}
