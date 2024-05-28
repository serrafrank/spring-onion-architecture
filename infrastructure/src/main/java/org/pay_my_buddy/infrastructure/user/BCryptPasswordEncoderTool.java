package org.pay_my_buddy.infrastructure.user;

import lombok.RequiredArgsConstructor;
import org.pay_my_buddy.entity.common.value_object.EncodedPassword;
import org.pay_my_buddy.entity.common.value_object.RawPassword;
import org.pay_my_buddy.entity.user.PasswordEncoderTool;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component("passwordEncoderTool")
@RequiredArgsConstructor
public class BCryptPasswordEncoderTool implements PasswordEncoderTool, PasswordEncoder {

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Override
    public EncodedPassword encode(RawPassword rawPassword) {
        final String encodedPassword = bCryptPasswordEncoder.encode(rawPassword.value());
        return EncodedPassword.of(encodedPassword);
    }

    @Override
    public boolean matches(RawPassword rawPassword, EncodedPassword encodedPassword) {
        return bCryptPasswordEncoder.matches(rawPassword.value(), encodedPassword.value());
    }

    @Override
    public String encode(CharSequence rawPassword) {
        return bCryptPasswordEncoder.encode(rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return bCryptPasswordEncoder.matches(rawPassword, encodedPassword);
    }
}
