package org.pay_my_buddy.application.user;

import lombok.RequiredArgsConstructor;
import org.pay_my_buddy.entity.commun.ApplicationService;
import org.pay_my_buddy.entity.commun.api.command.CommandHandler;
import org.pay_my_buddy.entity.commun.exception.UserNotFoundException;
import org.pay_my_buddy.entity.user.User;
import org.pay_my_buddy.entity.user.api.AddFriendCommand;
import org.pay_my_buddy.entity.user.spi.UserSpi;

/**
 * This class represents the use case of adding a friend to a user's friend list.
 * It implements the CommandHandler interface with AddFriendCommand as the command type.
 */
@ApplicationService
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
     * @throws UserNotFoundException if the user is not found
     * @throws FriendNotFoundException if the friend is not found
     * @throws UserIsAlreadyAFriendException if the friend is already in the user's friend list
     */
    @Override
    public void handle(AddFriendCommand command) {

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
    }
}