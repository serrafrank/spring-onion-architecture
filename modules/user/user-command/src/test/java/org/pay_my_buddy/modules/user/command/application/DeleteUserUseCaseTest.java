package org.pay_my_buddy.modules.user.command.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.pay_my_buddy.core.command.domain.event_storage.EventSourcingStorage;
import org.pay_my_buddy.modules.user.command.domain.UserAggregate;
import org.pay_my_buddy.modules.user.shared.UserId;
import org.pay_my_buddy.modules.user.shared.command.DeleteUserCommand;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.*;


@ExtendWith(MockitoExtension.class)
@DisplayName("DeleteUserUseCase unit tests")
class DeleteUserUseCaseTest {

    @Mock
    private EventSourcingStorage<UserAggregate, UserId> storage;

    @InjectMocks
    private DeleteUserUseCase useCase;


    @Test
    @DisplayName("GIVEN a user with no friends WHEN handle is called THEN the user is closed and saved")
    void testHandleNoFriends() {
        // GIVEN
        final UserId givenUserId = new UserId();
        final DeleteUserCommand givenCommand = new DeleteUserCommand(givenUserId);

        final UserAggregate givenUserAggregate = mock(UserAggregate.class);
        // The user has no friends
        given(givenUserAggregate.friends()).willReturn(Set.of());

        // Return the user aggregate from storage
        given(storage.getById(givenUserId)).willReturn(givenUserAggregate);

        // WHEN
        useCase.handle(givenCommand);

        // THEN
        // The user should be closed and saved
        then(givenUserAggregate).should(times(1)).close();
        then(storage).should(times(1)).save(givenUserAggregate);
    }

    @Test
    @DisplayName("GIVEN a user with some friends WHEN handle is called THEN each friend is updated and saved and the user is closed and saved")
    void testHandleWithFriends() {
        // GIVEN
        final UserId givenUserId = new UserId();
        final DeleteUserCommand givenCommand = new DeleteUserCommand(givenUserId);

        final UserAggregate givenUserAggregate = mock(UserAggregate.class, "userToClose");
        // Suppose the user has two friends
        final UserId friendId1 = new UserId();
        final UserId friendId2 = new UserId();
        final Set<UserId> givenFriends = new HashSet<>();
        givenFriends.add(friendId1);
        givenFriends.add(friendId2);

        given(givenUserAggregate.friends()).willReturn(givenFriends);

        // Return the user to close from storage
        given(storage.getById(givenUserId)).willReturn(givenUserAggregate);

        // Mock friend aggregates
        final UserAggregate friendAggregate1 = mock(UserAggregate.class, "friendAggregate1");
        final UserAggregate friendAggregate2 = mock(UserAggregate.class, "friendAggregate2");

        given(storage.getById(friendId1)).willReturn(friendAggregate1);
        given(storage.getById(friendId2)).willReturn(friendAggregate2);

        // add behavior for removing friend
        given(friendAggregate1.removeFriend(givenUserId)).willReturn(friendAggregate1);
        given(friendAggregate2.removeFriend(givenUserId)).willReturn(friendAggregate2);

        // WHEN
        useCase.handle(givenCommand);

        // THEN
        // Both friends should have been updated and saved
        then(friendAggregate1).should(times(1)).removeFriend(givenUserId);
        then(friendAggregate2).should(times(1)).removeFriend(givenUserId);
        then(storage).should(times(1)).save(friendAggregate1);
        then(storage).should(times(1)).save(friendAggregate2);

        // The user should be closed and saved
        then(givenUserAggregate).should(times(1)).close();
        then(storage).should(times(1)).save(givenUserAggregate);
    }

    @Test
    @DisplayName("GIVEN an exception occurs when retrieving the user to close WHEN handle is called THEN the exception is propagated and no saves occur")
    void testHandleUserNotFound() {
        // GIVEN
        final UserId givenUserId = new UserId();
        final DeleteUserCommand givenCommand = new DeleteUserCommand(givenUserId);

        // Simulate an exception when fetching the user
        given(storage.getById(givenUserId)).willThrow(new RuntimeException("User not found"));

        // WHEN/THEN
        assertThatThrownBy(() -> useCase.handle(givenCommand))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("User not found");

        // No saves should occur
        then(storage).should(never()).save(any());
    }

    @Test
    @DisplayName("GIVEN an exception occurs when retrieving a friend WHEN handle is called THEN the exception is propagated and no user save occurs")
    void testHandleFriendNotFound() {
        // GIVEN
        final UserId givenUserId = new UserId();
        final DeleteUserCommand givenCommand = new DeleteUserCommand(givenUserId);

        final UserAggregate givenUserAggregate = mock(UserAggregate.class, "userToClose");
        final UserId friendId = new UserId();
        given(givenUserAggregate.friends()).willReturn(Set.of(friendId));

        given(storage.getById(givenUserId)).willReturn(givenUserAggregate);
        given(storage.getById(friendId)).willThrow(new IllegalStateException("Friend not found"));

        // WHEN/THEN
        assertThatThrownBy(() -> useCase.handle(givenCommand))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Friend not found");

        // The user was never closed or saved due to the exception
        then(storage).should(never()).save(any(UserAggregate.class));
    }

    @Test
    @DisplayName("GIVEN an exception occurs when removing friend from a friend aggregate WHEN handle is called THEN the exception is propagated and no user is saved")
    void testHandleRemoveFriendFailsOnFriendAggregate() {
        // GIVEN
        final UserId givenUserId = new UserId();
        final DeleteUserCommand givenCommand = new DeleteUserCommand(givenUserId);

        final UserAggregate givenUserAggregate = mock(UserAggregate.class, "userToClose");
        final UserId friendId = new UserId();
        given(givenUserAggregate.friends()).willReturn(Set.of(friendId));

        given(storage.getById(givenUserId)).willReturn(givenUserAggregate);

        final UserAggregate friendAggregate = mock(UserAggregate.class, "friendAggregate");
        given(storage.getById(friendId)).willReturn(friendAggregate);
        // Simulate a failure when removing friend
        given(friendAggregate.removeFriend(givenUserId)).willThrow(new RuntimeException("Cannot remove friend"));

        // WHEN/THEN
        assertThatThrownBy(() -> useCase.handle(givenCommand))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Cannot remove friend");

        // The user was never closed or saved due to the exception
        then(storage).should(never()).save(givenUserAggregate);
    }

    @Test
    @DisplayName("GIVEN multiple calls WHEN each handle is executed THEN all calls execute independently and close the user successfully")
    void testHandleMultipleCalls() {
        // GIVEN
        final UserId userId1 = new UserId();
        final UserId userId2 = new UserId();
        final DeleteUserCommand command1 = new DeleteUserCommand(userId1);
        final DeleteUserCommand command2 = new DeleteUserCommand(userId2);

        final UserAggregate userAggregate1 = mock(UserAggregate.class, "userAggregate1");
        final UserAggregate userAggregate2 = mock(UserAggregate.class, "userAggregate2");

        // Both have no friends for simplicity
        given(userAggregate1.friends()).willReturn(Set.of());
        given(userAggregate2.friends()).willReturn(Set.of());

        given(storage.getById(userId1)).willReturn(userAggregate1);
        given(storage.getById(userId2)).willReturn(userAggregate2);

        // WHEN - first call
        useCase.handle(command1);

        // THEN - first call
        then(userAggregate1).should(times(1)).close();
        then(storage).should(times(1)).save(userAggregate1);

        // Reset mocks to simulate a clean scenario for the second call
        reset(storage, userAggregate1, userAggregate2);
        given(userAggregate2.friends()).willReturn(Set.of());
        given(storage.getById(userId2)).willReturn(userAggregate2);

        // WHEN - second call
        useCase.handle(command2);

        // THEN - second call
        then(userAggregate2).should(times(1)).close();
        then(storage).should(times(1)).save(userAggregate2);
    }

}
