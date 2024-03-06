package com.example.paymybuddy.application.shared.message_handler;

import java.lang.reflect.ParameterizedType;
import java.util.Arrays;

public interface Handler<T> {

    default boolean matchWith(Class<? extends T> type) {
        return Arrays.stream(((ParameterizedType) getClass().getGenericSuperclass())
                        .getActualTypeArguments())
                .distinct()
                .anyMatch(t -> t.getTypeName().equals(type.getName()));
    }
}