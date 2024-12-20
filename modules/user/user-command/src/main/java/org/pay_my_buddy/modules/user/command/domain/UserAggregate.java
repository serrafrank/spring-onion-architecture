package org.pay_my_buddy.modules.user.command.domain;

import lombok.*;
import lombok.experimental.Accessors;
import org.pay_my_buddy.core.command.domain.AbstractAggregateRoot;
import org.pay_my_buddy.core.command.domain.event_storage.AggregateEventListener;
import org.pay_my_buddy.core.framework.domain.exception.BusinessException;
import org.pay_my_buddy.core.framework.domain.exception.ConflictException;
import org.pay_my_buddy.modules.user.shared.UserId;
import org.pay_my_buddy.modules.user.shared.UserState;
import org.pay_my_buddy.modules.user.shared.command.UserCreatedEvent;
import org.pay_my_buddy.modules.user.shared.command.UserDeletedEvent;
import org.pay_my_buddy.modules.user.shared.query.UserFriendAddedEvent;
import org.pay_my_buddy.modules.user.shared.query.UserFriendRemovedEvent;

import java.util.HashSet;
import java.util.Set;

@Getter
@Accessors(fluent = true)
@EqualsAndHashCode(callSuper = true)
@ToString
public class UserAggregate extends AbstractAggregateRoot<UserAggregate.UserAggregateData, UserId> {

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Accessors(fluent = true)
    public static class UserAggregateData {

        private final Set<UserId> friends = new HashSet<>();
        private String firstname;
        private String lastname;
        private String email;
        private String password;
        private UserState currentState;


        public void initAggregate(String firstname, String lastname, String email, String password, UserState currentState) {
            this.firstname = firstname;
            this.lastname = lastname;
            this.email = email;
            this.password = password;
            this.currentState = currentState;
        }

        public void resetAggregate() {
            this.firstname = null;
            this.lastname = null;
            this.email = null;
            this.password = null;
            this.currentState = null;
            this.friends.clear();
        }

        public void resetAggregate(UserAggregateData aggregate) {
            this.firstname = aggregate.firstname;
            this.lastname = aggregate.lastname;
            this.email = aggregate.email;
            this.password = aggregate.password;
            this.currentState = aggregate.currentState;
            this.friends.clear();
            this.friends.addAll(aggregate.friends);
        }

        public boolean isClose() {
            return currentState == UserState.CLOSE;
        }
    }

    private UserAggregate(UserId id) {
        super(id, new UserAggregateData());
    }

    private UserAggregate() {
        super(new UserId(), new UserAggregateData());
    }

    public static UserAggregate newInstance(UserId id) {
        return new UserAggregate(id);
    }

    public static UserAggregate newInstance() {
        return new UserAggregate();
    }

    public UserAggregate create(String firstname, String lastname, String email, String password) {
        final var event = new UserCreatedEvent(id(), firstname, lastname, email, password, UserState.ACTIVE);
        addEvent(event);
        return this;
    }

    public UserAggregate addFriend(UserId friend) {
        if (this.data().isClose()) {
            throw BusinessException.wrap(new ConflictException("User is close"));
        }

        if (this.data().friends.contains(friend)) {
            throw BusinessException.wrap(new IllegalArgumentException("User is already a friendId"));
        }
        this.addEvent(new UserFriendAddedEvent(this.id(), friend));
        return this;
    }

    public UserAggregate removeFriend(UserId friend) {
        this.addEvent(new UserFriendRemovedEvent(this.id(), friend));
        return this;

    }

    public UserAggregate close() {
        if (this.data().isClose()) {
            throw BusinessException.wrap(new ConflictException("User is already close"));
        }
        this.addEvent(new UserDeletedEvent(this.id()));
        return this;
    }


    @AggregateEventListener
    public void on(UserCreatedEvent event) {
        this.data().initAggregate(event.firstname(), event.lastname(), event.email(), event.password(), UserState.ACTIVE);
    }

    @AggregateEventListener
    public void on(UserFriendAddedEvent event) {
        this.data().friends.add(event.friendId());
    }

    @AggregateEventListener
    public void on(UserFriendRemovedEvent event) {
        this.data().friends.remove(event.friendId());
    }

    @AggregateEventListener
    public void on(UserDeletedEvent event) {
        this.data().currentState = UserState.CLOSE;
    }

    @AggregateEventListener
    @Override
    public void on(CreateSnapshotAggregateEvent<UserAggregateData> event) {
        this.data().resetAggregate(event.aggregate());
    }

    @Override
    protected void resetAggregate() {
        this.data().resetAggregate();
    }


}
