package org.pay_my_buddy.modules.user.command.application;


import lombok.RequiredArgsConstructor;
import org.pay_my_buddy.core.command.application.CommandHandler;
import org.pay_my_buddy.core.command.domain.event_storage.EventSourcingStorage;
import org.pay_my_buddy.core.framework.domain.DomainService;
import org.pay_my_buddy.modules.user.command.domain.UserAggregate;
import org.pay_my_buddy.modules.user.shared.UserId;
import org.pay_my_buddy.modules.user.shared.command.DeleteUserCommand;

@DomainService
@RequiredArgsConstructor
public class DeleteUserUseCase implements CommandHandler<DeleteUserCommand, Void> {

    private final EventSourcingStorage<UserAggregate, UserId> storage;


    @Override
    public Void handle(DeleteUserCommand command) {
        final UserId userId = command.id();
        final UserAggregate userToClose = storage.getById(userId);

        removeFromFriends(userToClose, userId);

        userToClose.close();
        storage.save(userToClose);

        return null;
    }

    private void removeFromFriends(UserAggregate userToClose, UserId userId) {
        userToClose.friends().forEach(friendId -> {
            var friend = storage.getById(friendId).removeFriend(userId);
            storage.save(friend);
        });

    }
}
