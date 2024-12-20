package org.pay_my_buddy.modules.money_account.command.application;

import lombok.RequiredArgsConstructor;
import org.pay_my_buddy.core.command.application.CommandHandler;
import org.pay_my_buddy.core.command.domain.event_storage.EventSourcingStorage;
import org.pay_my_buddy.core.framework.domain.DomainService;
import org.pay_my_buddy.core.framework.domain.exception.BadArgumentException;
import org.pay_my_buddy.core.framework.domain.exception.BusinessException;
import org.pay_my_buddy.modules.money_account.command.domain.MoneyAccountAggregate;
import org.pay_my_buddy.modules.money_account.shared.MoneyAccountId;
import org.pay_my_buddy.modules.money_account.shared.command.CreditMoneyAccountCommand;
import org.pay_my_buddy.modules.money_account.shared.command.DebitMoneyAccountCommand;
import org.pay_my_buddy.modules.user.shared.query.UserIsActiveQuery;
import org.pay_my_buddy.modules.user.shared.query.UserQueryGateway;

@DomainService
@RequiredArgsConstructor
public class CreditMoneyAccountUseCase implements CommandHandler<CreditMoneyAccountCommand, Void> {

    private final UserQueryGateway userQueryGateway;
    private final EventSourcingStorage<MoneyAccountAggregate, MoneyAccountId> storage;

    @Override
    public Void handle(CreditMoneyAccountCommand command) {
        final MoneyAccountAggregate moneyAccountAggregate = storage.getById(command.id());

        UserIsActiveQuery userIsActiveQuery = new UserIsActiveQuery(moneyAccountAggregate.data().userId());

        if (!userQueryGateway.handle(userIsActiveQuery)){
            throw BusinessException.wrap(new BadArgumentException("User is not active"));
        }

        moneyAccountAggregate.debitBalance(command.amount(), command.currency());
        storage.save(moneyAccountAggregate);

        return null;
    }
}
