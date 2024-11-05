package org.pay_my_buddy.user.domain.create_user;

import lombok.RequiredArgsConstructor;
import org.pay_my_buddy.shared.domain.DomainService;
import org.pay_my_buddy.shared.domain.api.command.CommandHandler;
import org.pay_my_buddy.shared.domain.api.events.EventSourcingHandler;
import org.pay_my_buddy.shared.domain.api.query.QueryBus;
import org.pay_my_buddy.shared.domain.exception.BadArgumentException;
import org.pay_my_buddy.user.domain.UserAggregate;
import org.pay_my_buddy.user.domain.UserId;
import org.pay_my_buddy.user.domain.find_user_by_email.FindUserByEmailQuery;

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
