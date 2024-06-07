package org.pay_my_buddy.application.faker;

import net.datafaker.Faker;
import org.pay_my_buddy.entity.user.Email;

public record EmailFaker(String email) {

    private static final Faker faker = new Faker();

    public static EmailFaker of() {
        return new EmailFaker(FakerTool.data().internet().emailAddress());
    }

    public EmailFaker of(String email) {
        return new EmailFaker(email);
    }

    public Email build() {
        return Email.of(this.email);
    }
}
