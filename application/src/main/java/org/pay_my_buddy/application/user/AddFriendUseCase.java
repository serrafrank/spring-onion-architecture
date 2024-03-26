package org.pay_my_buddy.application.user;

import lombok.RequiredArgsConstructor;
import org.pay_my_buddy.entity.commun.ApplicationService;
import org.pay_my_buddy.entity.commun.api.command.CommandHandler;
import org.pay_my_buddy.entity.commun.exception.generic.ForbiddenException;
import org.pay_my_buddy.entity.commun.exception.generic.IllegalRequestException;
import org.pay_my_buddy.entity.user.User;
import org.pay_my_buddy.entity.user.api.AddFriendCommand;
import org.pay_my_buddy.entity.user.spi.UserSpi;

@ApplicationService
@RequiredArgsConstructor
public class AddFriendUseCase implements CommandHandler<AddFriendCommand> {

    private final UserSpi userSpi;

    @Override
    public void handle(AddFriendCommand command) {

        final User user = userSpi.findUser(command.userId())
                .orElseThrow(() -> new ForbiddenException("User does not exist"));

        if (!userSpi.existsById(command.friendId())) {
            throw new ForbiddenException("Friend does not exist");
        }

        if (user.isFriend(command.friendId())) {
            throw new IllegalRequestException("User is already a friend");
        }

        user.addFriend(command.friendId());

        userSpi.save(user);
    }
}
