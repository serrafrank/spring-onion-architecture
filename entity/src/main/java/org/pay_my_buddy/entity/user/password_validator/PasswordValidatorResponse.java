package org.pay_my_buddy.entity.user.password_validator;

import java.util.ArrayList;
import java.util.List;

public class PasswordValidatorResponse {
    private final List<String> errorMessages = new ArrayList<>();

    public void addErrorMessage(String message) {
        if (message != null) {
            errorMessages.add(message);
        }
    }

    public boolean isValid() {
        return errorMessages.isEmpty();
    }

    public List<String> messages() {
        return errorMessages;
    }
}