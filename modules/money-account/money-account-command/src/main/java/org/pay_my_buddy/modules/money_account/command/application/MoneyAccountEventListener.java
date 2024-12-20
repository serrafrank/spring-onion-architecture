package org.pay_my_buddy.modules.money_account.command.application;

import org.pay_my_buddy.modules.user.shared.command.UserCreatedEvent;

public interface MoneyAccountEventListener {

        void handleUserCreateEvent(UserCreatedEvent event);
}
