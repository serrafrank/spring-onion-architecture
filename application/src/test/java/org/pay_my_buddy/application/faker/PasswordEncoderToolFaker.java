package org.pay_my_buddy.application.faker;

import org.pay_my_buddy.entity.user.EncodedPassword;
import org.pay_my_buddy.entity.user.PasswordEncoderTool;
import org.pay_my_buddy.entity.user.RawPassword;

public class PasswordEncoderToolFaker implements PasswordEncoderTool {

    private final String passwordSuffix = "_encoded";

    @Override
    public EncodedPassword encode(RawPassword rawPassword) {
        return EncodedPassword.of(encodePassword(rawPassword.value()));
    }

    @Override
    public boolean matches(RawPassword rawPassword, EncodedPassword encodedPassword) {
        return encodedPassword.value().equals(encodePassword(rawPassword.value()));
    }

    public String encodePassword(String password) {
        return password + "_encoded";
    }
}
