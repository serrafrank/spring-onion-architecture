package org.pay_my_buddy.user_command.application;

import lombok.RequiredArgsConstructor;
import org.pay_my_buddy.api_command.CommandHandler;
import org.pay_my_buddy.api_command.EventSourcingStorage;
import org.pay_my_buddy.shared.exchange.user.UserId;
import org.pay_my_buddy.shared.exchange.user.command.DeleteUserCommand;
import org.pay_my_buddy.user_command.application.domain.UserAggregate;
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
