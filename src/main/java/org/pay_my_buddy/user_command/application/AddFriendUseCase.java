package org.pay_my_buddy.user_command.application;

import lombok.RequiredArgsConstructor;
import org.pay_my_buddy.api_command.CommandHandler;
import org.pay_my_buddy.api_command.EventSourcingStorage;
import org.pay_my_buddy.shared.exchange.user.UserId;
import org.pay_my_buddy.shared.exchange.user.command.AddFriendCommand;
import org.pay_my_buddy.user_command.application.domain.UserAggregate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddFriendUseCase implements CommandHandler<AddFriendCommand> {

	private final EventSourcingStorage<UserAggregate, UserId> storage;


	@Override
	public void handle(AddFriendCommand command) {

		final UserAggregate user = storage.getById(command.userId())
				.addFriend(command.friendId());

		final UserAggregate friend = storage.getById(command.friendId())
				.addFriend(command.userId());

		storage.save(user);
		storage.save(friend);
	}
}
