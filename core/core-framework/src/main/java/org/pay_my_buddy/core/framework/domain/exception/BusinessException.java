package org.pay_my_buddy.core.framework.domain.exception;

public class BusinessException extends RuntimeException {

    private BusinessException(Exception exception) {
        super(exception);
    }

    public static BusinessException wrap(Exception exception) {
        return new BusinessException(exception);
    }
}
