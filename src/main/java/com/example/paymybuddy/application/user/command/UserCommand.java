package com.example.paymybuddy.application.user.command;

import com.example.paymybuddy.core.user.valueobject.UserId;
import org.springframework.validation.annotation.Validated;

public interface UserCommand {

    UserId createUser(@Validated CreateUserCommand command);

    void debitUser(@Validated DebitUserCommand command);

    void creditUser(@Validated CreditUserCommand command);

}
