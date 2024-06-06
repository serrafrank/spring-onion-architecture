package org.pay_my_buddy.test_fixtures.faker;

import net.datafaker.Faker;
import org.pay_my_buddy.entity.user.Email;

public record EmailFaker(String email) {

    private static final Faker faker = new Faker();

    public EmailFaker of(String email) {
        return new EmailFaker(email);
    }

    public static EmailFaker of() {
        return new EmailFaker(faker.internet().emailAddress());
    }

    public Email build() {
        return Email.of(this.email);
    }
}
