package org.pay_my_buddy.modules.user.command.application;

import lombok.RequiredArgsConstructor;
import org.pay_my_buddy.core.command.application.CommandHandler;
import org.pay_my_buddy.core.command.domain.event_storage.EventSourcingStorage;
import org.pay_my_buddy.core.framework.application.QueryBus;
import org.pay_my_buddy.core.framework.domain.exception.BadArgumentException;
import org.pay_my_buddy.core.framework.domain.exception.BusinessException;
import org.pay_my_buddy.modules.user.command.domain.UserAggregate;
import org.pay_my_buddy.modules.user.shared.UserId;
import org.pay_my_buddy.modules.user.shared.command.CreateUserCommand;
import org.pay_my_buddy.modules.user.shared.query.FindUserByEmailQuery;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateUserUseCase implements CommandHandler<CreateUserCommand> {

    private final EventSourcingStorage<UserAggregate, UserId> storage;
    private final CreateUserPresenter presenter;
    private final QueryBus queryBus;


    @Override
    public void handle(CreateUserCommand command) {
        final FindUserByEmailQuery query = new FindUserByEmailQuery(command.email());
        if (queryBus.ask(query).isPresent()) {
            throw BusinessException.wrap(new BadArgumentException("Email already exists"));
        }

        UserAggregate userAggregate = UserAggregate.newInstance()
                .create(command.firstname(), command.lastname(), command.email(), command.password());
        storage.save(userAggregate);

        presenter.present(userAggregate.id());
    }
}
