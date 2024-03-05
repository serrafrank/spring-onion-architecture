package com.example.paymybuddy.application.shared.use_case.query;

public interface QueryUseCase<T extends Query<R>, R> {

    R execute(T command);

    Class<?> getRequestType();

    default boolean canHandle(Class<?> type) {
        return getRequestType().isAssignableFrom(type);
    }

}