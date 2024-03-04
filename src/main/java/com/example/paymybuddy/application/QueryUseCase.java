package com.example.paymybuddy.application;

import java.util.function.Consumer;
import java.util.function.Function;

public interface QueryUseCase<T extends Query<R>, R> extends UseCase<T, R> {
    R execute(T command);
}