package com.example.paymybuddy.application.shared.use_case.command;

import java.lang.reflect.ParameterizedType;

public abstract class AbstractCommandUseCase<T> implements CommandUseCase<T> {

    public Class<T> getRequestType() {
        return (Class<T>)
                ((ParameterizedType)getClass().getGenericSuperclass())
                        .getActualTypeArguments()[0];
    }
}