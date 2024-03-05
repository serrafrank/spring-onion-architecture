package com.example.paymybuddy.application.shared.use_case.command;

import com.example.paymybuddy.application.shared.use_case.UseCase;

public interface CommandUseCase<T> extends UseCase {

    void execute(T command);


}