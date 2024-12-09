package org.pay_my_buddy.shared.exception;

public class SystemException extends RuntimeException {

    private SystemException(Exception exception) {
        super(exception);
    }

    public static SystemException wrap(Exception exception) {
        return new SystemException(exception);
    }
}
