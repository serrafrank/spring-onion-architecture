package org.pay_my_buddy.modules.user.command.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.pay_my_buddy.core.framework.domain.exception.BusinessException;
import org.pay_my_buddy.modules.user.shared.UserId;
import org.pay_my_buddy.modules.user.shared.command.UserCreatedEvent;
import org.pay_my_buddy.modules.user.shared.command.UserDeletedEvent;
import org.pay_my_buddy.modules.user.shared.query.UserFriendAddedEvent;
import org.pay_my_buddy.modules.user.shared.query.UserFriendRemovedEvent;

import static org.junit.jupiter.api.Assertions.*;

class UserAggregateTest {

    @Test
    @DisplayName("GIVEN valid data WHEN create user THEN user is created and event is added")
    void createUser() {
        // GIVEN
        final UserId userId = new UserId();
        final UserAggregate user = UserAggregate.newInstance(userId);
        final String firstname = "John";
        final String lastname = "Doe";
        final String email = "john.doe@example.com";
        final String password = "password123";

        // WHEN
        user.create(firstname, lastname, email, password);

        // THEN
        assertEquals(firstname, user.data().firstname());
        assertEquals(lastname, user.data().lastname());
        assertEquals(email, user.data().email());
        assertEquals(password, user.data().password());
        assertEquals(UserAggregate.State.ACTIVE, user.data().currentState());
        assertTrue(user.uncommitedChanges().stream().anyMatch(event -> event.event() instanceof UserCreatedEvent));
    }

    @Test
    @DisplayName("GIVEN user and friend WHEN add friend THEN friend is added and event is added")
    void addFriend() {
        // GIVEN
        final UserId userId = new UserId();
        final UserId friendId = new UserId();
        final UserAggregate user = UserAggregate.newInstance(userId);

        // WHEN
        user.addFriend(friendId);

        // THEN
        assertTrue(user.data().friends().contains(friendId));
        assertTrue(user.uncommitedChanges().stream().anyMatch(event -> event.event() instanceof UserFriendAddedEvent));
    }

    @Test
    @DisplayName("GIVEN user with friend WHEN remove friend THEN friend is removed and event is added")
    void removeFriend() {
        // GIVEN
        final UserId userId = new UserId();
        final UserId friendId = new UserId();
        final UserAggregate user = UserAggregate.newInstance(userId);
        user.addFriend(friendId);

        // WHEN
        user.removeFriend(friendId);

        // THEN
        assertFalse(user.data().friends().contains(friendId));
        assertTrue(user.uncommitedChanges().stream().anyMatch(event -> event.event() instanceof UserFriendRemovedEvent));
    }

    @Test
    @DisplayName("GIVEN active user WHEN close user THEN user is closed and event is added")
    void closeUser() {
        // GIVEN
        final UserId userId = new UserId();
        final UserAggregate user = UserAggregate.newInstance(userId);
        user.create("John", "Doe", "john.doe@example.com", "password123");

        // WHEN
        user.close();

        // THEN
        assertEquals(UserAggregate.State.CLOSE, user.data().currentState());
        assertTrue(user.uncommitedChanges().stream().anyMatch(event -> event.event() instanceof UserDeletedEvent));
    }

    @Test
    @DisplayName("GIVEN closed user WHEN add friend THEN throw exception")
    void addFriendToClosedUser() {
        // GIVEN
        final UserId userId = new UserId();
        final UserId friendId = new UserId();
        final UserAggregate user = UserAggregate.newInstance(userId);
        user.create("John", "Doe", "john.doe@example.com", "password123");
        user.close();

        // WHEN & THEN
        assertThrows(BusinessException.class, () -> user.addFriend(friendId));
    }

    @Test
    @DisplayName("GIVEN user with existing friend WHEN add same friend THEN throw exception")
    void addExistingFriend() {
        // GIVEN
        final UserId userId = new UserId();
        final UserId friendId = new UserId();
        final UserAggregate user = UserAggregate.newInstance(userId);
        user.addFriend(friendId);

        // WHEN & THEN
        assertThrows(BusinessException.class, () -> user.addFriend(friendId));
    }

    @Test
    @DisplayName("GIVEN closed user WHEN close user THEN throw exception")
    void closeAlreadyClosedUser() {
        // GIVEN
        final UserId userId = new UserId();
        final UserAggregate user = UserAggregate.newInstance(userId);
        user.create("John", "Doe", "john.doe@example.com", "password123");
        user.close();

        // WHEN & THEN
        assertThrows(BusinessException.class, user::close);
    }


    @Test
    @DisplayName("GIVEN UserCreatedEvent WHEN on THEN user properties are set")
    void onUserCreatedEvent() {
        // GIVEN
        final UserId userId = new UserId();
        final UserAggregate user = UserAggregate.newInstance(userId);
        final UserCreatedEvent event = new UserCreatedEvent(userId, "John", "Doe", "john.doe@example.com", "password123");

        // WHEN
        user.on(event);

        // THEN
        assertEquals("John", user.data().firstname());
        assertEquals("Doe", user.data().lastname());
        assertEquals("john.doe@example.com", user.data().email());
        assertEquals("password123", user.data().password());
        assertEquals(UserAggregate.State.ACTIVE, user.data().currentState());
    }

    @Test
    @DisplayName("GIVEN UserFriendAddedEvent WHEN on THEN friend is added")
    void onUserFriendAddedEvent() {
        // GIVEN
        final UserId userId = new UserId();
        final UserId friendId = new UserId();
        final UserAggregate user = UserAggregate.newInstance(userId);
        final UserFriendAddedEvent event = new UserFriendAddedEvent(userId, friendId);

        // WHEN
        user.on(event);

        // THEN
        assertTrue(user.data().friends().contains(friendId));
    }

    @Test
    @DisplayName("GIVEN UserFriendRemovedEvent WHEN on THEN friend is removed")
    void onUserFriendRemovedEvent() {
        // GIVEN
        final UserId userId = new UserId();
        final UserId friendId = new UserId();
        final UserAggregate user = UserAggregate.newInstance(userId);
        user.addFriend(friendId);
        final UserFriendRemovedEvent event = new UserFriendRemovedEvent(userId, friendId);

        // WHEN
        user.on(event);

        // THEN
        assertFalse(user.data().friends().contains(friendId));
    }

    @Test
    @DisplayName("GIVEN UserDeletedEvent WHEN on THEN user is closed")
    void onUserDeletedEvent() {
        // GIVEN
        final UserId userId = new UserId();
        final UserAggregate user = UserAggregate.newInstance(userId);
        user.create("John", "Doe", "john.doe@example.com", "password123");
        final UserDeletedEvent event = new UserDeletedEvent(userId);

        // WHEN
        user.on(event);

        // THEN
        assertEquals(UserAggregate.State.CLOSE, user.data().currentState());
    }
}