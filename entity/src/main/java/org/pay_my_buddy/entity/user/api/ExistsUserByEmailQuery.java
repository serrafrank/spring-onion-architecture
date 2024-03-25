package org.pay_my_buddy.entity.user.api;

import org.pay_my_buddy.entity.commun.api.query.Query;
import org.pay_my_buddy.entity.commun.value_object.Email;

public record ExistsUserByEmailQuery(Email email) implements Query<Boolean> {

}
