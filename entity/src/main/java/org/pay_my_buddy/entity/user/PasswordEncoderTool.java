package org.pay_my_buddy.entity.user;

public interface PasswordEncoderTool {

    EncodedPassword encode(RawPassword rawPassword);

    boolean matches(RawPassword rawPassword, EncodedPassword encodedPassword);

    default boolean upgradeEncoding(EncodedPassword encodedPassword) {
        return false;
    }
}
