package org.pay_my_buddy.application.faker;

import org.pay_my_buddy.entity.commun.value_object.Email;

public class EmailFaker {

    public static Email create() {
        return Email.of(FakerTool.data().internet().emailAddress());
    }
}
