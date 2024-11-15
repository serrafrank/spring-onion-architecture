package org.pay_my_buddy.user_command.application;

import lombok.RequiredArgsConstructor;
import org.pay_my_buddy.api_command.AggregateStorage;
import org.pay_my_buddy.api_command.CommandHandler;
import org.pay_my_buddy.shared.exchange.user.UserId;
import org.pay_my_buddy.shared.exchange.user.command.AddFriendCommand;
import org.pay_my_buddy.user_command.application.domain.UserAggregate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddFriendUseCase implements CommandHandler<AddFriendCommand> {

	private final AggregateStorage<UserAggregate, UserId> aggregateStorage;


	@Override
	public void handle(AddFriendCommand command) {

		final UserAggregate user = aggregateStorage.getById(command.userId())
				.addFriend(command.friendId());

		final UserAggregate friend = aggregateStorage.getById(command.friendId())
				.addFriend(command.userId());

		aggregateStorage.save(user);
		aggregateStorage.save(friend);
	}
}
