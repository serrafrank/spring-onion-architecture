package org.pay_my_buddy.entity.user;

import org.pay_my_buddy.entity.common.value_object.EncodedPassword;
import org.pay_my_buddy.entity.common.value_object.RawPassword;

public interface PasswordEncoderTool {

    EncodedPassword encode(RawPassword rawPassword);

    boolean matches(RawPassword rawPassword, EncodedPassword encodedPassword);

    default boolean upgradeEncoding(EncodedPassword encodedPassword) {
        return false;
    }
}
