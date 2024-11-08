package org.pay_my_buddy.user_query.infrastructure;

import lombok.RequiredArgsConstructor;
import org.pay_my_buddy.shared.exception.NotFoundException;
import org.pay_my_buddy.shared.exchange.user.command.UserCreatedEvent;
import org.pay_my_buddy.shared.exchange.user.command.UserDeletedEvent;
import org.pay_my_buddy.shared.exchange.user.query.UserFriendAddedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserEventHandler {

	private final UserRepository userRepository;

	@EventListener
	public void handleUserCreated(UserCreatedEvent event) {
		UserEntity userEntity = new UserEntity(event);
		userRepository.save(userEntity);
	}

	@EventListener
	public void handleAddFriend(UserFriendAddedEvent event) {
		var user = userRepository.findById(event.user())
				.orElseThrow(() -> new NotFoundException("User not found"))
				.addFriend(event.friend());
		userRepository.save(user);
	}

	@EventListener
	public void handleUserDeleted(UserDeletedEvent event) {
		userRepository.deleteById(event.id());
	}
}
