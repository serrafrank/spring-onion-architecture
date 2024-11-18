package org.pay_my_buddy.user_query.infrastructure;

import lombok.RequiredArgsConstructor;
import org.pay_my_buddy.shared.exception.NotFoundException;
import org.pay_my_buddy.shared.exchange.user.UserId;
import org.pay_my_buddy.shared.exchange.user.command.UserCreatedEvent;
import org.pay_my_buddy.shared.exchange.user.command.UserDeletedEvent;
import org.pay_my_buddy.shared.exchange.user.query.UserFriendAddedEvent;
import org.pay_my_buddy.shared.exchange.user.query.UserFriendRemovedEvent;
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
		UserEntity user = getById(event.userId())
				.addFriend(event.friendId());
		userRepository.save(user);
	}

	@EventListener
	public void handleUserDeleted(UserDeletedEvent event) {
		userRepository.deleteById(event.userId().value());
	}

	@EventListener
	public void handleUserDeleted(UserFriendRemovedEvent event) {
		UserEntity user = getById(event.userId())
				.removeFriend(event.friendId());
		userRepository.save(user);
	}

	private UserEntity getById(UserId id){
		return userRepository.findById(id.value())
				.orElseThrow(() -> new NotFoundException("User not found"));
	}
}
