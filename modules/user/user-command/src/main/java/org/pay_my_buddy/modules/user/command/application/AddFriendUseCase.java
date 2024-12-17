package org.pay_my_buddy.modules.user.command.application;


import lombok.RequiredArgsConstructor;
import org.pay_my_buddy.core.command.application.CommandHandler;
import org.pay_my_buddy.core.command.domain.event_storage.EventSourcingStorage;
import org.pay_my_buddy.core.framework.domain.DomainService;
import org.pay_my_buddy.modules.user.command.domain.UserAggregate;
import org.pay_my_buddy.modules.user.shared.UserId;
import org.pay_my_buddy.modules.user.shared.command.AddFriendCommand;

@DomainService
@RequiredArgsConstructor
public class AddFriendUseCase implements CommandHandler<AddFriendCommand, Void> {

    private final EventSourcingStorage<UserAggregate, UserId> storage;

    @Override
    public Void handle(AddFriendCommand command) {

        final UserAggregate user = storage.getById(command.userId())
                .addFriend(command.friendId());

        final UserAggregate friend = storage.getById(command.friendId())
                .addFriend(command.userId());

        storage.save(user);
        storage.save(friend);
        return null;
    }
}
