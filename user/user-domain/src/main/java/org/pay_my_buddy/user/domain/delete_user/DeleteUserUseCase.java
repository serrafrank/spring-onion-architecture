package org.pay_my_buddy.user.domain.delete_user;

import lombok.RequiredArgsConstructor;
import org.pay_my_buddy.shared.domain.DomainService;
import org.pay_my_buddy.shared.domain.api.command.CommandHandler;
import org.pay_my_buddy.shared.domain.api.events.EventSourcingHandler;
import org.pay_my_buddy.user.domain.UserAggregate;

@DomainService
@RequiredArgsConstructor
public class DeleteUserUseCase implements CommandHandler<DeleteUserCommand> {

    private final EventSourcingHandler<UserAggregate> eventSourcingHandler;



    @Override
    public void handle(DeleteUserCommand command) {
		final UserAggregate aggregate = eventSourcingHandler.getById(command.id())
                .close();
        eventSourcingHandler.save(aggregate);

    }
}
