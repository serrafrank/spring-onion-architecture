package org.pay_my_buddy.user_command.application;

import lombok.RequiredArgsConstructor;
import org.pay_my_buddy.api_command.AggregateStorage;
import org.pay_my_buddy.api_command.CommandHandler;
import org.pay_my_buddy.shared.exchange.user.UserId;
import org.pay_my_buddy.shared.exchange.user.command.DeleteUserCommand;
import org.pay_my_buddy.user_command.application.domain.UserAggregate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteUserUseCase implements CommandHandler<DeleteUserCommand> {

	private final AggregateStorage<UserAggregate, UserId> aggregateStorage;


	@Override
	public void handle(DeleteUserCommand command) {
		var userId = command.id();
		final UserAggregate userToClose = aggregateStorage.getById(userId);
		
		userToClose.friends().forEach(friendId -> {
			var friend = aggregateStorage.getById(friendId).removeFriend(userId);
			aggregateStorage.save(friend);
		});

		userToClose.close();
		aggregateStorage.save(userToClose);

	}
}
