package org.pay_my_buddy.modules.user.command.application;

import lombok.RequiredArgsConstructor;
import org.pay_my_buddy.core.command.application.CommandHandler;
import org.pay_my_buddy.core.command.domain.event_storage.EventSourcingStorage;
import org.pay_my_buddy.modules.user.shared.UserId;
import org.pay_my_buddy.modules.user.command.domain.UserAggregate;
import org.pay_my_buddy.modules.user.shared.command.DeleteUserCommand;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteUserUseCase implements CommandHandler<DeleteUserCommand> {

    private final EventSourcingStorage<UserAggregate, UserId> storage;


    @Override
    public void handle(DeleteUserCommand command) {
        var userId = command.id();
        final UserAggregate userToClose = storage.getById(userId);

        userToClose.friends().forEach(friendId -> {
            var friend = storage.getById(friendId).removeFriend(userId);
            storage.save(friend);
        });

        userToClose.close();
        storage.save(userToClose);

    }
}
