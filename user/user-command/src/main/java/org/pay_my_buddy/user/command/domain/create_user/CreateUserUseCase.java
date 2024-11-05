package org.pay_my_buddy.user.command.domain.create_user;

import lombok.RequiredArgsConstructor;
import org.pay_my_buddy.shared.command.domain.CommandHandler;
import org.pay_my_buddy.shared.command.domain.events.EventSourcingHandler;
import org.pay_my_buddy.shared.common.DomainService;
import org.pay_my_buddy.shared.common.domain.api.query.QueryBus;
import org.pay_my_buddy.shared.common.domain.exception.BadArgumentException;
import org.pay_my_buddy.user.command.domain.UserAggregate;
import org.pay_my_buddy.user.common.domain.CreateUserCommand;
import org.pay_my_buddy.user.common.domain.FindUserByEmailQuery;
import org.pay_my_buddy.user.common.domain.UserId;

@DomainService
@RequiredArgsConstructor
public class CreateUserUseCase implements CommandHandler<CreateUserCommand> {


    private final EventSourcingHandler<UserAggregate> eventSourcingHandler;
    private final CreateUserPresenter presenter;
    private final QueryBus queryBus;


    @Override
    public void handle(CreateUserCommand command) {
        var query = new FindUserByEmailQuery(command.email());
        if (queryBus.ask(query).isPresent()) {
            throw new BadArgumentException("Email already exists");
        }

        UserId userId = UserId.newId();

        UserAggregate userAggregate = UserAggregate.create(userId, command.firstname(), command.lastname(), command.email(), command.password());
        eventSourcingHandler.save(userAggregate);

        presenter.present(userId);
    }
}
