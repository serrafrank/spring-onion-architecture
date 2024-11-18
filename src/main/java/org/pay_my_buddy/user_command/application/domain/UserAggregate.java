package org.pay_my_buddy.user_command.application.domain;

import java.util.HashSet;
import java.util.Set;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.pay_my_buddy.api_command.AbstractAggregateRoot;
import org.pay_my_buddy.api_command.event_storage.AggregateEventListener;
import org.pay_my_buddy.shared.exception.ConflictException;
import org.pay_my_buddy.shared.exchange.user.UserId;
import org.pay_my_buddy.shared.exchange.user.command.UserCreatedEvent;
import org.pay_my_buddy.shared.exchange.user.command.UserDeletedEvent;
import org.pay_my_buddy.shared.exchange.user.query.UserFriendAddedEvent;
import org.pay_my_buddy.shared.exchange.user.query.UserFriendRemovedEvent;

@Getter
@Accessors(fluent = true)
@EqualsAndHashCode(callSuper = true)
@ToString
public class UserAggregate extends AbstractAggregateRoot<UserId> {
	private final Set<UserId> friends = new HashSet<>();
	private String firstname;
	private String lastname;
	private String email;
	private String password;
	private State currentState;

	private UserAggregate(UserId id) {
		super(id);
	}

	private UserAggregate() {
		super(new UserId());
	}

	public static UserAggregate newInstance(UserId id) {
		return new UserAggregate(id);
	}

	public static UserAggregate newInstance() {
		return new UserAggregate();
	}

	public UserAggregate create(String firstname, String lastname, String email, String password) {
		final var event = new UserCreatedEvent(id(), firstname, lastname, email, password);
		addEvent(event);
		return this;
	}

	public UserAggregate addFriend(UserId friend) {
		if (this.currentState == State.CLOSE) {
			throw new ConflictException("User is close");
		}

		if (this.friends.contains(friend)) {
			throw new IllegalArgumentException("User is already a friendId");
		}
		this.addEvent(new UserFriendAddedEvent(this.id(), friend));
		return this;
	}

	public UserAggregate removeFriend(UserId friend) {
		this.addEvent(new UserFriendRemovedEvent(this.id(), friend));
		return this;

	}

	public UserAggregate close() {
		if (this.currentState == State.CLOSE) {
			throw new ConflictException("User is already close");
		}
		this.addEvent(new UserDeletedEvent(this.id()));
		return this;
	}


	@AggregateEventListener
	public void on(UserCreatedEvent event) {
		this.firstname = event.firstname();
		this.lastname = event.lastname();
		this.email = event.email();
		this.password = event.password();
		this.currentState = State.ACTIVE;
	}

	@AggregateEventListener
	public void on(UserFriendAddedEvent event) {
		this.friends.add(event.friendId());
	}

	@AggregateEventListener
	public void on(UserFriendRemovedEvent event) {
		this.friends.remove(event.friendId());
	}

	@AggregateEventListener
	public void on(UserDeletedEvent event) {
		this.currentState = State.CLOSE;
	}


	public enum State {
		ACTIVE,
		CLOSE
	}

}
