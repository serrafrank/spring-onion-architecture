package org.pay_my_buddy.application.faker;

import org.pay_my_buddy.entity.common.value_object.EncodedPassword;
import org.pay_my_buddy.entity.user.User;
import org.pay_my_buddy.entity.user.UserId;

import java.util.Set;

public final class UserFaker {

    public static User create() {
        return new User(
                UserId.of(),
                FakerTool.data().name().firstName(),
                FakerTool.data().name().lastName(),
                EmailFaker.create(),
                new EncodedPassword("password"),
                Set.of()
        );
    }
}