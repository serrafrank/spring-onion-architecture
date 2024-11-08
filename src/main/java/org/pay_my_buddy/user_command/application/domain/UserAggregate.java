package org.pay_my_buddy.user_command.application.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.pay_my_buddy.api_command.AggregateRoot;
import org.pay_my_buddy.api_command.event_storage.AggregateEventHandler;
import org.pay_my_buddy.shared.exception.ConflictException;
import org.pay_my_buddy.shared.exchange.user.UserId;
import org.pay_my_buddy.shared.exchange.user.command.UserCreatedEvent;
import org.pay_my_buddy.shared.exchange.user.command.UserDeletedEvent;
import org.pay_my_buddy.shared.exchange.user.query.UserFriendAddedEvent;

import java.util.HashSet;
import java.util.Set;

@Getter
@Accessors(fluent = true, chain = true)
@EqualsAndHashCode(callSuper = true)
public class UserAggregate extends AggregateRoot<UserAggregate, UserId> {
	private String firstname;
	private String lastname;
	private String email;
	private String password;

	private final Set<UserId> friends = new HashSet<>();

	private State currentState;

	private UserAggregate(UserId id) {
		super(id);
	}
	private UserAggregate() {
		super(UserId.createRandomUnique());
	}

    public static UserAggregate newInstance(UserId id) {
		return new UserAggregate(id);
	}

	public static UserAggregate newInstance() {
		return new UserAggregate();
	}

	public static UserAggregate create(String firstname, String lastname, String email, String password) {
		final var id = UserId.createRandomUnique();
		final var event = new UserCreatedEvent(id, firstname, lastname, email, password);
		return newInstance().addEvent(event);
	}

	public UserAggregate addFriend(UserId id) {
		if (this.currentState == State.CLOSE) {
			throw new ConflictException("User is close");
		}

		if (this.friends.contains(id)) {
			throw new IllegalArgumentException("User is already a friend");
		}
		this.addEvent(new UserFriendAddedEvent(this.id(), id));
		return this;
	}

	public UserAggregate close() {
		if (this.currentState == State.CLOSE) {
			throw new ConflictException("User is already close");
		}
		this.addEvent(new UserDeletedEvent(this.id()));
		return this;
	}


	@AggregateEventHandler
	public void on(UserCreatedEvent event) {
		this.firstname = event.firstname();
		this.lastname = event.lastname();
		this.email = event.email();
		this.password = event.password();
		this.currentState = State.ACTIVE;
	}

	@AggregateEventHandler
	public void on(UserFriendAddedEvent event) {
		this.friends.add(event.friend());
	}

	@AggregateEventHandler
	public void on(UserDeletedEvent event) {
		this.currentState = State.CLOSE;
	}


	public enum State {
		ACTIVE,
		CLOSE
	}

}
