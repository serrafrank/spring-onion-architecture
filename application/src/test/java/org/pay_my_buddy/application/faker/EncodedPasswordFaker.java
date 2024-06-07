package org.pay_my_buddy.application.faker;

import net.datafaker.Faker;
import org.pay_my_buddy.entity.user.EncodedPassword;
import org.pay_my_buddy.entity.user.RawPassword;


public record EncodedPasswordFaker(String encodedPassword) {
    private static final PasswordEncoderToolFaker passwordEncoderTool = new PasswordEncoderToolFaker();

    private static final RawPassword rawPassword = RawPasswordFaker.of().build();

    private static final Faker faker = new Faker();

    public static EncodedPasswordFaker of() {
        var encodedPassword = passwordEncoderTool.encodePassword(rawPassword.value());
        return new EncodedPasswordFaker(encodedPassword);
    }

    public EncodedPasswordFaker of(String encodedPassword) {
        return new EncodedPasswordFaker(encodedPassword);
    }

    public EncodedPassword build() {
        return EncodedPassword.of(this.encodedPassword);
    }

    public RawPassword rawPassword() {
        return rawPassword;
    }
}
