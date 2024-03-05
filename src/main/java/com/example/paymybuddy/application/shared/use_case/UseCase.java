package com.example.paymybuddy.application.shared.use_case;

public interface UseCase {

    Class<?> getRequestType();

    default boolean canHandle(Class<?> type) {
        return getRequestType().isAssignableFrom(type);
    }
}