package org.pay_my_buddy.user.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.pay_my_buddy.shared.domain.api.events.Event;
import org.pay_my_buddy.shared.domain.domain_event.UserCreatedEvent;
import org.pay_my_buddy.shared.domain.domain_event.UserDeletedEvent;
import org.pay_my_buddy.shared.domain.domain_event.UserFriendAddedEvent;
import org.pay_my_buddy.shared.domain.entity.AggregateRoot;
import org.pay_my_buddy.shared.domain.entity.EntityId;
import org.pay_my_buddy.shared.domain.exception.ConflictException;

import java.util.HashSet;
import java.util.Set;

@Getter
@Accessors(fluent = true, chain = true)
@EqualsAndHashCode(callSuper = true)
public class UserAggregate extends AggregateRoot<UserId> {
    private String firstname;
    private String lastname;
    private String email;
    private String password;

    private final Set<EntityId> friends = new HashSet<>();
    
    private State currentState;

    private UserAggregate() {
    }

    public static UserAggregate newInstance(){
        return new UserAggregate();
    }

    public static UserAggregate create(UserId id, String firstname, String lastname, String email, String password) {
        var user = new UserAggregate();
        var event = new UserCreatedEvent(id, firstname, lastname, email, password);
        user.raiseEvent(event);
        return user;
    }

    public UserAggregate addFriend(EntityId id) {
        if (this.currentState == State.CLOSE) {
            throw new ConflictException("User is close");
        }
        
        if(this.friends.contains(id)) {
            throw new IllegalArgumentException("User is already a friend");
        }
        
        var event = new UserFriendAddedEvent(this.id, id);
        
        this.raiseEvent(event);
        return this;
    }

    public UserAggregate close() {
        if (this.currentState == State.CLOSE) {
            throw new ConflictException("User is already close");
        }
        var event = new UserDeletedEvent(this.id);
        this.raiseEvent(event);
        return this;
    }


    protected void applyChange(Event event){
        switch (event.getClass().getSimpleName()) {
            case "UserCreatedEvent" -> applyEvent((UserCreatedEvent) event);
            case "UserFriendAddedEvent" -> applyEvent((UserFriendAddedEvent) event);
            case "UserDeletedEvent" -> applyEvent((UserDeletedEvent) event);
            default -> throw new IllegalArgumentException("Event '" + event.getClass().getSimpleName() + "' not supported");
        }
    }


    private void applyEvent(UserCreatedEvent event) {
        this.id = UserId.of(event.id());
        this.firstname = event.firstname();
        this.lastname = event.lastname();
        this.email = event.email();
        this.currentState = State.ACTIVE;
    }

    private void applyEvent(UserFriendAddedEvent event) {
        this.id = UserId.of(event.user());
        this.friends.add(event.friend());
    }

    private void applyEvent(UserDeletedEvent event) {
        this.id = UserId.of(event.id());
        this.currentState = State.CLOSE;
    }


    public enum State {
        ACTIVE,
        CLOSE
    }

}
