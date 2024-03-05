package com.example.paymybuddy.application.shared.use_case.query;

import com.example.paymybuddy.application.shared.use_case.UseCase;

public interface QueryUseCase<T extends Query<R>, R> extends UseCase {

    R execute(T command);

}