package org.pay_my_buddy.modules.user.command.application;

import lombok.RequiredArgsConstructor;
import org.pay_my_buddy.core.command.application.CommandHandler;
import org.pay_my_buddy.core.command.domain.event_storage.EventSourcingStorage;
import org.pay_my_buddy.core.framework.domain.DomainService;
import org.pay_my_buddy.core.framework.domain.exception.BadArgumentException;
import org.pay_my_buddy.core.framework.domain.exception.BusinessException;
import org.pay_my_buddy.modules.user.command.domain.UserAggregate;
import org.pay_my_buddy.modules.user.shared.UserId;
import org.pay_my_buddy.modules.user.shared.command.CreateUserCommand;
import org.pay_my_buddy.modules.user.shared.query.FindUserByEmailQuery;
import org.pay_my_buddy.modules.user.shared.query.UserQueryGateway;

@DomainService
@RequiredArgsConstructor
public class CreateUserUseCase implements CommandHandler<CreateUserCommand, UserId> {

    private final EventSourcingStorage<UserAggregate, UserId> storage;
    private final UserQueryGateway userQueryGateway;


    @Override
    public UserId handle(CreateUserCommand command) {
        if (isEmailAlreadyExists(command.email())) {
            throw BusinessException.wrap(new BadArgumentException("Email already exists"));
        }

        UserAggregate userAggregate = UserAggregate.newInstance().create(command.firstname(), command.lastname(), command.email(), command.password());
        storage.save(userAggregate);

        return userAggregate.id();
    }

    private boolean isEmailAlreadyExists(String email) {
        final FindUserByEmailQuery query = new FindUserByEmailQuery(email);
        return userQueryGateway.handle(query).isPresent();
    }
}
