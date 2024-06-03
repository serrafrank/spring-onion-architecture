package org.pay_my_buddy.entity.user;

import lombok.EqualsAndHashCode;
import lombok.Value;
import org.pay_my_buddy.entity.AbstractValueObject;

/**
 * The type Encoded password.
 */
@Value
@EqualsAndHashCode(callSuper = true)
public class EncodedPassword extends AbstractValueObject<String> {

    /**
     * Instantiates a new Encoded password.
     *
     * @param value the value
     */
    private EncodedPassword(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        this.value = value;
    }

    /**
     * Of encoded password.
     *
     * @param encodedPassword the encoded password
     * @return the encoded password
     */
    public static EncodedPassword of(String encodedPassword) {
        return new EncodedPassword(encodedPassword);
    }

    public static EncodedPassword of(EncodedPassword encodedPassword) {
        return new EncodedPassword(encodedPassword.value());
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
