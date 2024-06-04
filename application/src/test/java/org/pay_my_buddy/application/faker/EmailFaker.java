package org.pay_my_buddy.application.faker;

import org.pay_my_buddy.entity.user.Email;

public class EmailFaker {

    public static Email create() {
        return Email.of(FakerTool.data().internet().emailAddress());
    }
}
