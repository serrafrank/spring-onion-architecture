package org.pay_my_buddy.modules.money_account.command.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pay_my_buddy.modules.money_account.command.application.MoneyAccountEventListener;
import org.pay_my_buddy.modules.money_account.shared.command.CreateMoneyAccountCommand;
import org.pay_my_buddy.modules.money_account.shared.command.MoneyAccountGateway;
import org.pay_my_buddy.modules.user.shared.command.UserCreatedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Currency;

@Slf4j
@Service
@RequiredArgsConstructor
public class MoneyAccountEventListenerImpl implements MoneyAccountEventListener {
    private final MoneyAccountGateway moneyAccountGateway;

    @EventListener
    @Override
    public void handleUserCreateEvent(UserCreatedEvent event) {
        log.info("Handling user created event: {}", event);
        moneyAccountGateway.handle(new CreateMoneyAccountCommand(event.id(), Currency.getInstance("EUR")));
    }
}
