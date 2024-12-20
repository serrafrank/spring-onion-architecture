package org.pay_my_buddy.modules.user.query.domain;

import org.pay_my_buddy.modules.user.shared.command.UserCreatedEvent;
import org.pay_my_buddy.modules.user.shared.command.UserDeletedEvent;
import org.pay_my_buddy.modules.user.shared.query.UserFriendAddedEvent;
import org.pay_my_buddy.modules.user.shared.query.UserFriendRemovedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;

public interface UserEventListener {
    @Order(10)
    @EventListener
    void listen(UserCreatedEvent event);

    @Order(10)
    @EventListener
    void listen(UserFriendAddedEvent event);

    @Order(10)
    @EventListener
    void listen(UserDeletedEvent event);

    @Order(10)
    @EventListener
    void listen(UserFriendRemovedEvent event);
}
