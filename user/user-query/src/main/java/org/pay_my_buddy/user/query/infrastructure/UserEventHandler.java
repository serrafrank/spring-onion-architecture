package org.pay_my_buddy.user.query.infrastructure;

import lombok.RequiredArgsConstructor;
import org.pay_my_buddy.shared.common.domain.exception.NotFoundException;
import org.pay_my_buddy.user.common.domain.UserCreatedEvent;
import org.pay_my_buddy.user.common.domain.UserDeletedEvent;
import org.pay_my_buddy.user.common.domain.UserFriendAddedEvent;
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
		var user = userRepository.findById(event.user().toString())
				.orElseThrow(() -> new NotFoundException("User not found"))
				.addFriend(event.friend());
		userRepository.save(user);
	}

	@EventListener
	public void handleUserDeleted(UserDeletedEvent event) {
		userRepository.deleteById(event.id().toString());
	}
}
