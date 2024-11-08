package org.pay_my_buddy.user_command.application;

import lombok.RequiredArgsConstructor;
import org.pay_my_buddy.api_command.CommandHandler;
import org.pay_my_buddy.api_command.EventSourcingHandler;
import org.pay_my_buddy.shared.exchange.user.command.AddFriendCommand;
import org.pay_my_buddy.user_command.application.domain.UserAggregate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddFriendUseCase implements CommandHandler<AddFriendCommand> {

	private final EventSourcingHandler<UserAggregate> eventSourcingHandler;


	@Override
	public void handle(AddFriendCommand command) {

		final UserAggregate user = eventSourcingHandler.getById(command.userId())
				.addFriend(command.friendId());

		final UserAggregate friend = eventSourcingHandler.getById(command.friendId())
				.addFriend(command.userId());

		eventSourcingHandler.save(user);
		eventSourcingHandler.save(friend);
	}
}
