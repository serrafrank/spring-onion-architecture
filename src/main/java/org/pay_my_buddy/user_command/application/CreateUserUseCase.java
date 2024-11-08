package org.pay_my_buddy.user_command.application;

import lombok.RequiredArgsConstructor;
import org.pay_my_buddy.api_command.CommandHandler;
import org.pay_my_buddy.api_command.EventSourcingHandler;
import org.pay_my_buddy.api_query.QueryBus;
import org.pay_my_buddy.shared.exception.BadArgumentException;
import org.pay_my_buddy.shared.exchange.user.command.CreateUserCommand;
import org.pay_my_buddy.shared.exchange.user.query.FindUserByEmailQuery;
import org.pay_my_buddy.shared.exchange.user.UserId;
import org.pay_my_buddy.user_command.application.domain.UserAggregate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateUserUseCase implements CommandHandler<CreateUserCommand> {


    private final EventSourcingHandler<UserAggregate> eventSourcingHandler;
    private final CreateUserPresenter presenter;
    private final QueryBus queryBus;


    @Override
    public void handle(CreateUserCommand command) {
		final FindUserByEmailQuery query = new FindUserByEmailQuery(command.email());
        if (queryBus.ask(query).isPresent()) {
            throw new BadArgumentException("Email already exists");
        }

        UserId userId = UserId.createRandomUnique();

        UserAggregate userAggregate = UserAggregate.create(command.firstname(), command.lastname(), command.email(), command.password());
        eventSourcingHandler.save(userAggregate);

        presenter.present(userId);
    }
}