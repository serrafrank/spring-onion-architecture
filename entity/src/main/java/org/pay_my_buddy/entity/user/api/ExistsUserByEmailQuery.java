package org.pay_my_buddy.entity.user.api;

import org.pay_my_buddy.entity.common.api.query.Query;
import org.pay_my_buddy.entity.common.value_object.Email;

public record ExistsUserByEmailQuery(Email email) implements Query<Boolean> {

}
