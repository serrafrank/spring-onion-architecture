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
import org.pay_my_buddy.modules.user.shared.command.AddFriendCommand;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.*;


@ExtendWith(MockitoExtension.class)
@DisplayName("AddFriendUseCase unit tests")
class AddFriendUseCaseTest {

    @Mock
    private EventSourcingStorage<UserAggregate, UserId> storage;

    @InjectMocks
    private AddFriendUseCase useCase;

    @Test
    @DisplayName("GIVEN a valid command WHEN handle is called THEN both user and friend aggregates are updated and saved successfully")
    void testHandleUpdatesAndSaves() {
        // GIVEN
        final UserId givenUserId = new UserId();
        final UserId givenFriendId = new UserId();
        final AddFriendCommand givenCommand = new AddFriendCommand(givenUserId, givenFriendId);

        final UserAggregate givenUserAggregate = mock(UserAggregate.class, "userAggregate");
        final UserAggregate givenFriendAggregate = mock(UserAggregate.class, "friendAggregate");

        // Return these aggregates from storage
        given(storage.getById(givenUserId)).willReturn(givenUserAggregate);
        given(storage.getById(givenFriendId)).willReturn(givenFriendAggregate);

        // Simulate addFriend methods returning the same aggregates for chaining
        given(givenUserAggregate.addFriend(givenFriendId)).willReturn(givenUserAggregate);
        given(givenFriendAggregate.addFriend(givenUserId)).willReturn(givenFriendAggregate);

        // WHEN
        final Void result = useCase.handle(givenCommand);

        // THEN
        // Verify interactions:
        then(storage).should(times(1)).getById(givenUserId);
        then(storage).should(times(1)).getById(givenFriendId);
        then(givenUserAggregate).should(times(1)).addFriend(givenFriendId);
        then(givenFriendAggregate).should(times(1)).addFriend(givenUserId);
        then(storage).should(times(1)).save(givenUserAggregate);
        then(storage).should(times(1)).save(givenFriendAggregate);
        assertThat(result).isNull();
    }

    @Test
    @DisplayName("GIVEN a command and getById for user throws an exception WHEN handle is called THEN no saves are performed and exception propagates")
    void testHandleGetByIdUserThrowsException() {
        // GIVEN
        final UserId givenUserId = new UserId();
        final UserId givenFriendId = new UserId();
        final AddFriendCommand givenCommand = new AddFriendCommand(givenUserId, givenFriendId);

        given(storage.getById(givenUserId)).willThrow(new RuntimeException("User not found"));

        // WHEN/THEN
        assertThatThrownBy(() -> useCase.handle(givenCommand))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("User not found");

        // No saves should occur
        then(storage).should(never()).save(any());
    }

    @Test
    @DisplayName("GIVEN a command and getById for friend throws an exception WHEN handle is called THEN no saves are performed and exception propagates")
    void testHandleGetByIdFriendThrowsException() {
        // GIVEN
        final UserId givenUserId = new UserId();
        final UserId givenFriendId = new UserId();
        final AddFriendCommand givenCommand = new AddFriendCommand(givenUserId, givenFriendId);

        final UserAggregate givenUserAggregate = mock(UserAggregate.class);
        given(storage.getById(givenUserId)).willReturn(givenUserAggregate);
        given(storage.getById(givenFriendId)).willThrow(new RuntimeException("Friend not found"));

        // WHEN/THEN
        assertThatThrownBy(() -> useCase.handle(givenCommand))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Friend not found");

        // No saves should occur
        then(storage).should(never()).save(any());
    }

    @Test
    @DisplayName("GIVEN a command and addFriend on user throws an exception WHEN handle is called THEN no saves are performed and exception propagates")
    void testHandleAddFriendUserThrowsException() {
        // GIVEN
        final UserId givenUserId = new UserId();
        final UserId givenFriendId = new UserId();
        final AddFriendCommand givenCommand = new AddFriendCommand(givenUserId, givenFriendId);

        final UserAggregate givenUserAggregate = mock(UserAggregate.class);

        given(storage.getById(givenUserId)).willReturn(givenUserAggregate);

        // Simulate user addFriend failing
        given(givenUserAggregate.addFriend(givenFriendId)).willThrow(new IllegalArgumentException("Already a friend"));

        // WHEN/THEN
        assertThatThrownBy(() -> useCase.handle(givenCommand))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Already a friend");

        // No saves should occur
        then(storage).should(never()).save(any());
    }

    @Test
    @DisplayName("GIVEN a command and addFriend on friend throws an exception WHEN handle is called THEN no friend save is performed and exception propagates")
    void testHandleAddFriendFriendThrowsException() {
        // GIVEN
        final UserId givenUserId = new UserId();
        final UserId givenFriendId = new UserId();
        final AddFriendCommand givenCommand = new AddFriendCommand(givenUserId, givenFriendId);

        final UserAggregate givenUserAggregate = mock(UserAggregate.class);
        final UserAggregate givenFriendAggregate = mock(UserAggregate.class);

        given(storage.getById(givenUserId)).willReturn(givenUserAggregate);
        given(storage.getById(givenFriendId)).willReturn(givenFriendAggregate);

        given(givenUserAggregate.addFriend(givenFriendId)).willReturn(givenUserAggregate);
        // Simulate friend addFriend failing
        given(givenFriendAggregate.addFriend(givenUserId)).willThrow(new IllegalStateException("Cannot add this friend"));

        // WHEN/THEN
        assertThatThrownBy(() -> useCase.handle(givenCommand))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Cannot add this friend");

        // User aggregate might have been updated but since the friend failed, no saves should occur
        then(storage).should(never()).save(any());
    }

    @Test
    @DisplayName("GIVEN multiple calls to handle WHEN each is executed THEN all calls execute independently and save both aggregates")
    void testHandleMultipleCalls() {
        // GIVEN
        final UserId givenUserId = new UserId();
        final UserId givenFriendId = new UserId();
        final AddFriendCommand firstCommand = new AddFriendCommand(givenUserId, givenFriendId);

        final UserAggregate userAggregate = mock(UserAggregate.class, "userAggregate");
        final UserAggregate friendAggregate = mock(UserAggregate.class, "friendAggregate");

        given(storage.getById(givenUserId)).willReturn(userAggregate);
        given(storage.getById(givenFriendId)).willReturn(friendAggregate);
        given(userAggregate.addFriend(givenFriendId)).willReturn(userAggregate);
        given(friendAggregate.addFriend(givenUserId)).willReturn(friendAggregate);

        // WHEN - first call
        useCase.handle(firstCommand);

        // THEN - first call
        then(storage).should(times(1)).getById(givenUserId);
        then(storage).should(times(1)).getById(givenFriendId);
        then(userAggregate).should(times(1)).addFriend(givenFriendId);
        then(friendAggregate).should(times(1)).addFriend(givenUserId);
        then(storage).should(times(1)).save(userAggregate);
        then(storage).should(times(1)).save(friendAggregate);

        // Reset mocks for second call
        reset(storage, userAggregate, friendAggregate);

        final AddFriendCommand secondCommand = new AddFriendCommand(givenUserId, givenFriendId);
        given(storage.getById(givenUserId)).willReturn(userAggregate);
        given(storage.getById(givenFriendId)).willReturn(friendAggregate);
        given(userAggregate.addFriend(givenFriendId)).willReturn(userAggregate);
        given(friendAggregate.addFriend(givenUserId)).willReturn(friendAggregate);

        // WHEN - second call
        useCase.handle(secondCommand);

        // THEN - second call
        then(storage).should(times(1)).getById(givenUserId);
        then(storage).should(times(1)).getById(givenFriendId);
        then(userAggregate).should(times(1)).addFriend(givenFriendId);
        then(friendAggregate).should(times(1)).addFriend(givenUserId);
        then(storage).should(times(1)).save(userAggregate);
        then(storage).should(times(1)).save(friendAggregate);
    }
}
