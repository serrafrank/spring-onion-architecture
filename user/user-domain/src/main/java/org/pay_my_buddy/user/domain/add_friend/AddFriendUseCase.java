package org.pay_my_buddy.user.domain.add_friend;

import lombok.RequiredArgsConstructor;
import org.pay_my_buddy.shared.domain.DomainService;
import org.pay_my_buddy.shared.domain.api.command.CommandHandler;
import org.pay_my_buddy.shared.domain.api.events.EventSourcingHandler;
import org.pay_my_buddy.user.domain.UserAggregate;

@DomainService
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
