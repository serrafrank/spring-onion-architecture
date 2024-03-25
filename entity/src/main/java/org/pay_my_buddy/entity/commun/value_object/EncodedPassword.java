package org.pay_my_buddy.entity.commun.value_object;

public record EncodedPassword(String value) {

    public EncodedPassword {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
    }

    public static EncodedPassword of(String encodedPassword) {
        return new EncodedPassword(encodedPassword);
    }

    @Override
    public String toString() {
        return value;
    }
}
