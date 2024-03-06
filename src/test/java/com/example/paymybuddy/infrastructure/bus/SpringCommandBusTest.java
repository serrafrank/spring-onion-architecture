package com.example.paymybuddy.infrastructure.bus;

import com.example.paymybuddy.application.shared.message_handler.CommandApi;
import com.example.paymybuddy.application.account.use_case.credit_account.CreditAccountCommand;
import com.example.paymybuddy.application.account.use_case.credit_account.CreditAccountUseCase;
import com.example.paymybuddy.application.shared.message_handler.Command;
import com.example.paymybuddy.application.shared.value_object.Amount;
import com.example.paymybuddy.application.user.domain.UserId;
import org.apache.commons.lang3.ClassUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.ParameterizedType;
import java.util.Arrays;

@SpringBootTest
class SpringCommandBusTest {

    @Autowired
    private CommandApi springCommandBus;
    @Autowired
    CreditAccountUseCase creditAccountUseCase;

    @Test
    void dispatch() {
        Command command = new CreditAccountCommand(new UserId(), new Amount());

        ClassUtils.getAllInterfaces(creditAccountUseCase.getClass())
                .stream()
                .flatMap(clazz -> Arrays.stream(clazz.getGenericInterfaces()))
                .flatMap(type -> Arrays.stream(((ParameterizedType) type).getActualTypeArguments()))
                .distinct()
                .toList();

        creditAccountUseCase.matchWith(command.getClass());
        springCommandBus.dispatch(command);
    }
}