package org.pay_my_buddy.modules.money_account.command.application;

import lombok.RequiredArgsConstructor;
import org.pay_my_buddy.core.command.application.CommandHandler;
import org.pay_my_buddy.core.command.domain.event_storage.EventSourcingStorage;
import org.pay_my_buddy.core.framework.domain.DomainService;
import org.pay_my_buddy.core.framework.domain.exception.BadArgumentException;
import org.pay_my_buddy.core.framework.domain.exception.BusinessException;
import org.pay_my_buddy.modules.money_account.command.domain.MoneyAccountAggregate;
import org.pay_my_buddy.modules.money_account.shared.MoneyAccountId;
import org.pay_my_buddy.modules.money_account.shared.command.CreateMoneyAccountCommand;
import org.pay_my_buddy.modules.user.shared.query.UserIsActiveQuery;
import org.pay_my_buddy.modules.user.shared.query.UserQueryGateway;

@DomainService
@RequiredArgsConstructor
public class CreateMoneyAccountUseCase  implements CommandHandler<CreateMoneyAccountCommand, Void> {

    private final UserQueryGateway userQueryGateway;
    private final EventSourcingStorage<MoneyAccountAggregate, MoneyAccountId> storage;

    @Override
    public Void handle(CreateMoneyAccountCommand command) {
        UserIsActiveQuery userIsActiveQuery = new UserIsActiveQuery(command.userId());

        if (!userQueryGateway.handle(userIsActiveQuery)){
            throw BusinessException.wrap(new BadArgumentException("User is not active"));
        }

        MoneyAccountAggregate moneyAccountAggregate = MoneyAccountAggregate.newInstance().create(command.userId(), command.currency());
        storage.save(moneyAccountAggregate);

        return null;
    }
}
