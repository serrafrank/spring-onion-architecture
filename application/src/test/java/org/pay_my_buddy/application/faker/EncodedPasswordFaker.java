package org.pay_my_buddy.application.faker;

import lombok.experimental.Accessors;
import org.pay_my_buddy.entity.user.EncodedPassword;

public class EncodedPasswordFaker {

    private static EncodedPassword create() {
        return new EncodedPasswordFakerBuilder().create();
    }

    public static EncodedPasswordFakerBuilder with() {
        return new EncodedPasswordFakerBuilder();
    }

    @Accessors(fluent = true, chain = true)
    static class EncodedPasswordFakerBuilder {
        private int minLength = 8;
        private int maxLength = 20;
        private boolean specialCharacters = true;
        private boolean uppercaseLetters = true;
        private boolean digits = true;

        public EncodedPassword create() {
            final String password = FakerTool.data().internet().password(
                    minLength,
                    maxLength,
                    specialCharacters,
                    uppercaseLetters,
                    digits
            );

            return EncodedPassword.of(password);
        }
    }


}
