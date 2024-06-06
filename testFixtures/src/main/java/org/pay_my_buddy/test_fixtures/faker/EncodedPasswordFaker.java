package org.pay_my_buddy.test_fixtures.faker;

import net.datafaker.Faker;
import org.pay_my_buddy.entity.user.EncodedPassword;


public record EncodedPasswordFaker(String encodedPassword) {

    private static final Faker faker = new Faker();

        public EncodedPasswordFaker of(String encodedPassword) {
            return new EncodedPasswordFaker(encodedPassword);
        }

        public static EncodedPasswordFaker of() {
            return new EncodedPasswordFaker(faker.internet().password(8, 15));
        }

        public EncodedPassword build() {
            return EncodedPassword.of(this.encodedPassword);
        }
}
