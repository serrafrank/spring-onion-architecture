package org.pay_my_buddy.modules.user.command.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.pay_my_buddy.core.command.domain.event_storage.EventSourcingStorage;
import org.pay_my_buddy.core.framework.domain.exception.BusinessException;
import org.pay_my_buddy.core.framework.domain.exception.NotFoundException;
import org.pay_my_buddy.modules.user.command.domain.UserAggregate;
import org.pay_my_buddy.modules.user.shared.UserId;
import org.pay_my_buddy.modules.user.shared.command.CloseUserAccountCommand;
import org.pay_my_buddy.modules.user.shared.command.RemoveFriendCommand;
import org.pay_my_buddy.modules.user.shared.command.UserCommandGateway;

import java.util.Collections;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("CloseUserAccountUseCase unit tests")
class CloseUserAccountUseCaseTest {

    @Mock
    private EventSourcingStorage<UserAggregate, UserId> storage;
    @Mock
    private UserCommandGateway commandGateway;
    @InjectMocks
    private CloseUserAccountUseCase useCase;

    @Test
    @DisplayName("GIVEN a user with no friends WHEN handle is called THEN the user is closed and saved without calling remove friend commands")
    void testHandleUserWithNoFriends() {
        // GIVEN
        // Prepare a user with no friends
        final UserId givenUserId = new UserId();
        final CloseUserAccountCommand givenCommand = new CloseUserAccountCommand(givenUserId);

        final UserAggregate givenUserAggregate = mock(UserAggregate.class, "userAggregate");
        given(storage.getById(givenUserId)).willReturn(givenUserAggregate);

        // Mock userAggregate data to have empty friends
        given(givenUserAggregate.data()).willReturn(new UserAggregateDataMock(Collections.emptySet()));

        // WHEN
        final Void result = useCase.handle(givenCommand);

        // THEN
        // Verify that no remove friend command was sent
        then(commandGateway).should(never()).handle(any(RemoveFriendCommand.class));

        // Verify user was closed and saved
        then(givenUserAggregate).should(times(1)).close();
        then(storage).should(times(1)).save(givenUserAggregate);

        // Result should be null as per the method contract
        assertThat(result).isNull();
    }

    @Test
    @DisplayName("GIVEN a user with multiple friends WHEN handle is called THEN remove friend commands are sent for each friend and user is closed")
    void testHandleUserWithMultipleFriends() {
        // GIVEN
        // Prepare a user with friends
        final UserId givenUserId = new UserId();
        final UserId friendId1 = new UserId();
        final UserId friendId2 = new UserId();

        final CloseUserAccountCommand givenCommand = new CloseUserAccountCommand(givenUserId);

        final UserAggregate givenUserAggregate = mock(UserAggregate.class, "userAggregate");
        given(storage.getById(givenUserId)).willReturn(givenUserAggregate);

        // The user has two friends
        given(givenUserAggregate.data()).willReturn(new UserAggregateDataMock(Set.of(friendId1, friendId2)));

        // WHEN
        final Void result = useCase.handle(givenCommand);

        // THEN
        // Check that remove friend commands were sent for each friend
        ArgumentCaptor<RemoveFriendCommand> captor = ArgumentCaptor.forClass(RemoveFriendCommand.class);
        then(commandGateway).should(times(2)).handle(captor.capture());
        final var sentCommands = captor.getAllValues();
        assertThat(sentCommands).extracting(RemoveFriendCommand::userId).containsExactlyInAnyOrder(friendId1, friendId2);
        assertThat(sentCommands).extracting(RemoveFriendCommand::friendId).allMatch(id -> id.equals(givenUserId));

        // Verify user was closed and saved
        then(givenUserAggregate).should(times(1)).close();
        then(storage).should(times(1)).save(givenUserAggregate);

        // Result should be null
        assertThat(result).isNull();
    }

    @Test
    @DisplayName("GIVEN a user that does not exist WHEN handle is called THEN an exception is thrown")
    void testHandleNonExistentUser() {
        // GIVEN
        final UserId givenUserId = new UserId();
        final CloseUserAccountCommand givenCommand = new CloseUserAccountCommand(givenUserId);

        // Simulate user not found
        given(storage.getById(givenUserId)).willThrow(BusinessException.wrap(new NotFoundException("User not found")));

        // WHEN/THEN
        assertThatThrownBy(() -> useCase.handle(givenCommand))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("User not found");

        // Verify that no commands were sent and user was not saved
        then(commandGateway).shouldHaveNoInteractions();
        then(storage).should(never()).save(any(UserAggregate.class));
    }

    @Test
    @DisplayName("GIVEN a user with friends WHEN removeFriend commands fail THEN exception is propagated and user is not saved")
    void testHandleRemoveFriendFailure() {
        // GIVEN
        final UserId givenUserId = new UserId();
        final UserId friendId = new UserId();
        final CloseUserAccountCommand givenCommand = new CloseUserAccountCommand(givenUserId);

        final UserAggregate givenUserAggregate = mock(UserAggregate.class, "userAggregate");
        given(storage.getById(givenUserId)).willReturn(givenUserAggregate);
        given(givenUserAggregate.data()).willReturn(new UserAggregateDataMock(Set.of(friendId)));

        // Simulate an exception when removing friend
        doThrow(new RuntimeException("Failed to remove friend"))
                .when(commandGateway).handle(new RemoveFriendCommand(friendId, givenUserId));

        // WHEN/THEN
        assertThatThrownBy(() -> useCase.handle(givenCommand))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Failed to remove friend");

        // Verify that user was never closed or saved due to exception
        then(givenUserAggregate).should(never()).close();
        then(storage).should(never()).save(any(UserAggregate.class));
    }

    @Test
    @DisplayName("GIVEN multiple calls WHEN handle is called repeatedly THEN each call is independent")
    void testHandleMultipleCalls() {
        // GIVEN
        // First scenario: user with no friends
        final UserId userId1 = new UserId();
        final CloseUserAccountCommand command1 = new CloseUserAccountCommand(userId1);
        final UserAggregate userAggregate1 = mock(UserAggregate.class, "userAggregate1");
        given(storage.getById(userId1)).willReturn(userAggregate1);
        given(userAggregate1.data()).willReturn(new UserAggregateDataMock(Collections.emptySet()));

        // WHEN - first call
        useCase.handle(command1);

        // THEN - first call
        then(userAggregate1).should(times(1)).close();
        then(storage).should(times(1)).save(userAggregate1);

        // Reset mocks for second scenario
        reset(storage, commandGateway, userAggregate1);

        // GIVEN - second scenario: user with a friend
        final UserId userId2 = new UserId();
        final UserId friendId2 = new UserId();
        final CloseUserAccountCommand command2 = new CloseUserAccountCommand(userId2);
        final UserAggregate userAggregate2 = mock(UserAggregate.class, "userAggregate2");
        given(storage.getById(userId2)).willReturn(userAggregate2);
        given(userAggregate2.data()).willReturn(new UserAggregateDataMock(Set.of(friendId2)));

        // WHEN - second call
        useCase.handle(command2);

        // THEN - second call
        ArgumentCaptor<RemoveFriendCommand> captor = ArgumentCaptor.forClass(RemoveFriendCommand.class);
        then(commandGateway).should(times(1)).handle(captor.capture());
        RemoveFriendCommand sentCommand = captor.getValue();
        assertThat(sentCommand.friendId()).isEqualTo(userId2);
        assertThat(sentCommand.userId()).isEqualTo(friendId2);

        then(userAggregate2).should(times(1)).close();
        then(storage).should(times(1)).save(userAggregate2);
    }


    // Helper class to simulate a UserAggregate.Data object that returns friends
    static class UserAggregateDataMock extends UserAggregate.UserAggregateData {
        private final Set<UserId> friends;

        UserAggregateDataMock(Set<UserId> friends) {
            this.friends = friends;
        }

        public Set<UserId> friends() {
            return friends;
        }
    }

}
