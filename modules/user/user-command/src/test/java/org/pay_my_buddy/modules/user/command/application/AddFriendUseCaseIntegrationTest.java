package org.pay_my_buddy.modules.user.command.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.pay_my_buddy.modules.user.command.domain.UserAggregate;
import org.pay_my_buddy.modules.user.shared.UserId;
import org.pay_my_buddy.modules.user.shared.command.AddFriendCommand;

import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("Integration tests for AddFriendUseCase")
class AddFriendUseCaseIntegrationTest {

    private InMemoryEventSourcingStorage storage;
    private AddFriendUseCase useCase;

    @BeforeEach
    void setUp() {
        // GIVEN: An in-memory event sourcing storage and two existing user aggregates
        this.storage = new InMemoryEventSourcingStorage();
        this.useCase = new AddFriendUseCase(storage);

        // Create two users
        final UserId userId = new UserId();
        final UserId friendId = new UserId();

        final UserAggregate user = UserAggregate.newInstance(userId).create("John", "Doe", "john.doe@example.com", "pass123");
        final UserAggregate friend = UserAggregate.newInstance(friendId).create("Jane", "Smith", "jane.smith@example.com", "pass456");

        // Save them in storage to simulate existing users
        storage.save(user);
        storage.save(friend);
    }

    @Test
    @DisplayName("GIVEN two existing users WHEN we handle AddFriendCommand THEN both users have each other in their friend lists after the operation")
    void testAddFriendSuccessfully() {
        // GIVEN
        // Retrieve the two existing users
        final UserId givenUserId = storage.getByEmail("john.doe@example.com");   // see getByEmail() in the in-memory impl
        final UserId givenFriendId = storage.getByEmail("jane.smith@example.com");
        final AddFriendCommand givenCommand = new AddFriendCommand(givenUserId, givenFriendId);

        // WHEN
        // Execute the use case to add each as friend to the other
        useCase.handle(givenCommand);

        // THEN
        // After adding friends, retrieve both users from the storage
        final UserAggregate updatedUser = storage.getById(givenUserId);
        final UserAggregate updatedFriend = storage.getById(givenFriendId);

        // Verify that each user has the other in their friend set
        assertThat(updatedUser.friends()).contains(givenFriendId);
        assertThat(updatedFriend.friends()).contains(givenUserId);
    }
}

