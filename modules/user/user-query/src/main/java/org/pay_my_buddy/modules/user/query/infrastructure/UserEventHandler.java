package org.pay_my_buddy.modules.user.query.infrastructure;

import lombok.RequiredArgsConstructor;
import org.pay_my_buddy.core.framework.domain.exception.BusinessException;
import org.pay_my_buddy.core.framework.domain.exception.NotFoundException;
import org.pay_my_buddy.modules.user.shared.UserId;
import org.pay_my_buddy.modules.user.shared.command.UserCreatedEvent;
import org.pay_my_buddy.modules.user.shared.command.UserDeletedEvent;
import org.pay_my_buddy.modules.user.shared.query.UserFriendAddedEvent;
import org.pay_my_buddy.modules.user.shared.query.UserFriendRemovedEvent;
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

    private UserEntity getById(UserId id) {
        return userRepository.findById(id.value())
                .orElseThrow(() -> BusinessException.wrap(new NotFoundException("User not found")));
    }
}
