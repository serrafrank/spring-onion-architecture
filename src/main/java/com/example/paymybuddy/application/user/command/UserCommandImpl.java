package com.example.paymybuddy.application.user.command;

import com.example.paymybuddy.application.user.UserService;
import com.example.paymybuddy.core.user.valueobject.UserId;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor

public class UserCommandImpl implements UserCommand {
    private final UserService userService;


    @Override
    public UserId createUser(CreateUserCommand command) {
        return userService.createUser(command.firstName(), command.lastName(), command.email(), command.password());
    }


    @Override
    public void debitUser(DebitUserCommand command) {
        userService.debitUser(new UserId(command.id()), command.amount());
    }

    @Override
    public void creditUser(CreditUserCommand command) {
        userService.creditUser(new UserId(command.id()), command.amount());
    }
}
