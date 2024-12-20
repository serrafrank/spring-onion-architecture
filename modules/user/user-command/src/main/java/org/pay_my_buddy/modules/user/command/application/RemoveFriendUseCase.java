package org.pay_my_buddy.modules.user.command.application;


import lombok.RequiredArgsConstructor;
import org.pay_my_buddy.core.command.application.CommandHandler;
import org.pay_my_buddy.core.command.domain.event_storage.EventSourcingStorage;
import org.pay_my_buddy.core.framework.domain.DomainService;
import org.pay_my_buddy.modules.user.command.domain.UserAggregate;
import org.pay_my_buddy.modules.user.shared.UserId;
import org.pay_my_buddy.modules.user.shared.command.RemoveFriendCommand;

@DomainService
@RequiredArgsConstructor
public class RemoveFriendUseCase implements CommandHandler<RemoveFriendCommand, Void> {

    private final EventSourcingStorage<UserAggregate, UserId> storage;

    @Override
    public Void handle(RemoveFriendCommand command) {

        final UserAggregate user = storage.getById(command.userId())
                .removeFriend(command.friendId());

        final UserAggregate friend = storage.getById(command.friendId())
                .removeFriend(command.userId());

        storage.save(user);
        storage.save(friend);
        return null;
    }
}
