package org.pay_my_buddy.application.faker;

import org.pay_my_buddy.entity.user.RawPassword;


public record RawPasswordFaker(String rawPassword) {

    public static RawPasswordFaker of() {
        return new RawPasswordFaker(FakerTool.password());

    }

    public static RawPasswordFaker of(String rawPassword) {
        return new RawPasswordFaker(rawPassword);
    }

    public RawPassword build() {
        return RawPassword.of(this.rawPassword);
    }
}
