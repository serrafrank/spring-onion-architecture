package org.pay_my_buddy.user_command.application;

import lombok.RequiredArgsConstructor;
import org.pay_my_buddy.api_command.CommandHandler;
import org.pay_my_buddy.api_command.EventSourcingHandler;
import org.pay_my_buddy.shared.exchange.user.command.DeleteUserCommand;
import org.pay_my_buddy.user_command.application.domain.UserAggregate;
import org.springframework.stereotype.Service;

@Service
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
