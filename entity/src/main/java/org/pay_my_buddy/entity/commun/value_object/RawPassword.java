package org.pay_my_buddy.entity.commun.value_object;

public record RawPassword(String value) {

    private static final String PASSWORD_PATTERN = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
    private static final String PASSWORD_ERROR_MESSAGE = "Password must contain at least 8 characters, including 1 digit, 1 lowercase letter, 1 uppercase letter, and 1 special character";


    public RawPassword {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        if (!value.matches(PASSWORD_PATTERN)) {
            throw new IllegalArgumentException(PASSWORD_ERROR_MESSAGE);
        }
    }

    public static RawPassword of(String password) {
        return new RawPassword(password);
    }

    @Override
    public String toString() {
        return value;
    }
}
