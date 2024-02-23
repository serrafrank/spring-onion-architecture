package com.example.paymybuddy.application.user.command;

import com.example.paymybuddy.core.user.valueobject.UserId;
import org.springframework.validation.annotation.Validated;

public interface UserCommand {

    UserId createUser(CreateUserCommand command);

    void debitUser(DebitUserCommand command);

    void creditUser(CreditUserCommand command);

}
