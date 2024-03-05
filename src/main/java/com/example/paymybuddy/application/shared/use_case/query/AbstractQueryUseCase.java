package com.example.paymybuddy.application.shared.use_case.query;


import java.lang.reflect.ParameterizedType;

public abstract class AbstractQueryUseCase<T extends Query<R>, R> implements QueryUseCase<T, R> {

    public Class<T> getRequestType() {
        return (Class<T>)
                ((ParameterizedType)getClass().getGenericSuperclass())
                        .getActualTypeArguments()[0];
    }
}