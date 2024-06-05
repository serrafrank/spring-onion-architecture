package org.pay_my_buddy.application.use_case.user.command.add_friend;

import lombok.RequiredArgsConstructor;
import org.pay_my_buddy.application.common.DomainService;
import org.pay_my_buddy.application.common.api.CommandHandler;
import org.pay_my_buddy.application.common.api.EventList;
import org.pay_my_buddy.application.use_case.user.events.FriendAddedEvent;
import org.pay_my_buddy.application.use_case.user.UserSpi;
import org.pay_my_buddy.entity.exception.UserNotFoundException;
import org.pay_my_buddy.entity.user.User;

/**
 * This class represents the use case of adding a friend to a user's friend list.
 * It implements the CommandHandler interface with AddFriendCommand as the command type.
 */
@DomainService
@RequiredArgsConstructor
public class AddFriendUseCase implements CommandHandler<AddFriendCommand> {

    // UserSpi is used to interact with the User entity
    private final UserSpi userSpi;

    /**
     * This method handles the execution of the AddFriendCommand.
     * It first checks if the user and the friend exist.
     * If they do, it checks if the friend is already in the user's friend list.
     * If not, it adds the friend to the user's friend list and saves the user entity.
     *
     * @param command the command to be executed, which contains the user ID and the friend ID
     * @throws UserNotFoundException         if the user is not found
     * @throws FriendNotFoundException       if the friend is not found
     * @throws UserIsAlreadyAFriendException if the friend is already in the user's friend list
     */
    @Override
    public EventList handle(AddFriendCommand command) {

        // Find the user by ID
        final User user = userSpi.findUser(command.userId())
                .orElseThrow(() -> new UserNotFoundException(command.userId()));

        // Check if the friend exists
        if (!userSpi.existsById(command.friendId())) {
            throw new FriendNotFoundException(command.friendId());
        }

        // Check if the friend is already in the user's friend list
        if (user.isFriend(command.friendId())) {
            throw new UserIsAlreadyAFriendException(command.friendId());
        }

        // Add the friend to the user's friend list
        user.addFriend(command.friendId());

        // Save the user entity
        userSpi.save(user);

        return EventList.of(FriendAddedEvent.of(command, command.userId(), command.friendId()));
    }
}