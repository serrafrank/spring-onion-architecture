package org.pay_my_buddy.modules.user.command.application;


import lombok.RequiredArgsConstructor;
import org.pay_my_buddy.core.command.application.CommandHandler;
import org.pay_my_buddy.core.command.domain.event_storage.EventSourcingStorage;
import org.pay_my_buddy.core.framework.domain.DomainService;
import org.pay_my_buddy.modules.user.command.domain.UserAggregate;
import org.pay_my_buddy.modules.user.shared.UserId;
import org.pay_my_buddy.modules.user.shared.command.CloseUserAccountCommand;
import org.pay_my_buddy.modules.user.shared.command.RemoveFriendCommand;
import org.pay_my_buddy.modules.user.shared.command.UserCommandGateway;

import java.util.Set;

@DomainService
@RequiredArgsConstructor
public class CloseUserAccountUseCase implements CommandHandler<CloseUserAccountCommand, Void> {

    private final EventSourcingStorage<UserAggregate, UserId> storage;
    private final UserCommandGateway commandGateway;


    @Override
    public Void handle(CloseUserAccountCommand command) {
        final UserId userId = command.id();
        final UserAggregate userToClose = storage.getById(userId);


        userToClose
                .data()
                .friends()
                .forEach(friendId -> {
                    var removeFriendCommand = new RemoveFriendCommand(friendId, userId);
                    commandGateway.handle(removeFriendCommand);
                });

        userToClose.close();
        storage.save(userToClose);

        return null;
    }
}
