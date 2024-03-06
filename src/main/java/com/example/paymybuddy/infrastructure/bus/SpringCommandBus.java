package com.example.paymybuddy.infrastructure.bus;

import com.example.paymybuddy.application.shared.message_handler.CommandApi;
import com.example.paymybuddy.application.shared.message_handler.CommandHandler;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SpringCommandBus implements CommandApi {

    @Getter
    private final List<CommandHandler<?>> handlers;

}
