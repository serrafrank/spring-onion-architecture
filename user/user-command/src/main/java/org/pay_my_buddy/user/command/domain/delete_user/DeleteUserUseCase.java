package org.pay_my_buddy.user.command.domain.delete_user;

import lombok.RequiredArgsConstructor;
import org.pay_my_buddy.shared.command.domain.CommandHandler;
import org.pay_my_buddy.shared.command.domain.events.EventSourcingHandler;
import org.pay_my_buddy.shared.common.DomainService;
import org.pay_my_buddy.user.command.domain.UserAggregate;
import org.pay_my_buddy.user.common.domain.DeleteUserCommand;

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
