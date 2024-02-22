package com.example.paymybuddy.core.user;

import com.example.paymybuddy.core.user.valueobject.UserId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserAggregateTest {

    private UserAggregate userAggregate;
    private UserId friendId;

    @BeforeEach
    void setUp() {
        userAggregate = new UserAggregate("John", "Doe", "john.doe@example.com", "password123");
        friendId = new UserId();
    }

    @Test
    void shouldCreateUserWithGivenParameters() {
        assertEquals("John", userAggregate.getFirstName());
        assertEquals("Doe", userAggregate.getLastName());
        assertEquals("john.doe@example.com", userAggregate.getEmail());
        assertEquals("password123", userAggregate.getPassword());
    }

    @Test
    void shouldAddFriendToUser() {
        userAggregate.addFriend(friendId);
        assertTrue(userAggregate.isFriend(friendId));
    }

    @Test
    void shouldRemoveFriendFromUser() {
        userAggregate.addFriend(friendId);
        userAggregate.removeFriend(friendId);
        assertFalse(userAggregate.isFriend(friendId));
    }

    @Test
    void shouldNotBeFriendIfNotAdded() {
        assertFalse(userAggregate.isFriend(friendId));
    }

    @Test
    void shouldThrowExceptionWhenAddingNullFriend() {
        assertThrows(IllegalArgumentException.class, () -> userAggregate.addFriend(null));
    }

    @Test
    void shouldThrowExceptionWhenRemovingNullFriend() {
        assertThrows(IllegalArgumentException.class, () -> userAggregate.removeFriend(null));
    }

    @Test
    void shouldThrowExceptionWhenCheckingNullFriend() {
        assertThrows(IllegalArgumentException.class, () -> userAggregate.isFriend(null));
    }
}