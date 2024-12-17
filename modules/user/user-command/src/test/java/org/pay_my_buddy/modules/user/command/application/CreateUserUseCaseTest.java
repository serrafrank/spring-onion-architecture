package org.pay_my_buddy.modules.user.command.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.pay_my_buddy.core.command.domain.event_storage.EventSourcingStorage;
import org.pay_my_buddy.core.framework.domain.exception.BadArgumentException;
import org.pay_my_buddy.core.framework.domain.exception.BusinessException;
import org.pay_my_buddy.modules.user.command.domain.UserAggregate;
import org.pay_my_buddy.modules.user.mock.UserEntityProjectionMock;
import org.pay_my_buddy.modules.user.shared.UserId;
import org.pay_my_buddy.modules.user.shared.command.CreateUserCommand;
import org.pay_my_buddy.modules.user.shared.query.FindUserByEmailQuery;
import org.pay_my_buddy.modules.user.shared.query.UserQueryGateway;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("CreateUserUseCase unit tests")
class CreateUserUseCaseTest {

    @Mock
    private EventSourcingStorage<UserAggregate, UserId> storage;
    @Mock
    private UserQueryGateway userQueryGateway;
    @InjectMocks
    private CreateUserUseCase useCase;


    @Test
    @DisplayName("GIVEN a valid command with a new email WHEN handle is called THEN a new user is created, saved, and the userId is returned")
    void testHandleWithNewEmail() {
        // GIVEN
        // Prepare test data: a new email that doesn't exist in the system
        final String givenEmail = "new.user@example.com";
        final String givenFirstname = "John";
        final String givenLastname = "Doe";
        final String givenPassword = "securePass123";
        final CreateUserCommand givenCommand = new CreateUserCommand(givenFirstname, givenLastname, givenEmail, givenPassword);

        // The query returns empty, meaning the email does not exist
        given(userQueryGateway.handle(new FindUserByEmailQuery(givenEmail))).willReturn(Optional.empty());

        // WHEN
        // Call the method under test
        final UserId result = useCase.handle(givenCommand);

        // THEN
        // Verify that storage.save was called with a UserAggregate
        final ArgumentCaptor<UserAggregate> captor = ArgumentCaptor.forClass(UserAggregate.class);
        then(storage).should(times(1)).save(captor.capture());
        final UserAggregate savedAggregate = captor.getValue();

        // Check that the returned UserId matches the aggregate's id
        assertThat(result).isEqualTo(savedAggregate.id());

        // No exception should be thrown, user saved successfully
    }

    @Test
    @DisplayName("GIVEN a command with an existing email WHEN handle is called THEN a BusinessException with BadArgumentException is thrown and no user is saved")
    void testHandleWithExistingEmail() {
        // GIVEN
        // Prepare test data: an email that already exists
        final String givenEmail = "existing.user@example.com";
        final String givenFirstname = "Jane";
        final String givenLastname = "Smith";
        final String givenPassword = "anotherPass456";
        final CreateUserCommand givenCommand = new CreateUserCommand(givenFirstname, givenLastname, givenEmail, givenPassword);

        // The query returns an optional result, meaning the email already exists
        given(userQueryGateway.handle(new FindUserByEmailQuery(givenEmail))).willReturn(Optional.of(new UserEntityProjectionMock()));

        // WHEN/THEN
        // Expect a BusinessException with a BadArgumentException cause
        assertThatThrownBy(() -> useCase.handle(givenCommand))
                .isInstanceOf(BusinessException.class)
                .hasCauseInstanceOf(BadArgumentException.class)
                .hasMessageContaining("Email already exists");

        // Verify that no user was saved since exception was thrown
        then(storage).should(never()).save(any(UserAggregate.class));
    }

    @Test
    @DisplayName("GIVEN a command but userQueryGateway fails unexpectedly WHEN handle is called THEN the exception is propagated and no user is saved")
    void testHandleWithUnexpectedErrorOnEmailCheck() {
        // GIVEN
        // Prepare test data: some email check that leads to an unexpected error
        final String givenEmail = "error.user@example.com";
        final String givenFirstname = "Error";
        final String givenLastname = "Prone";
        final String givenPassword = "failPass";
        final CreateUserCommand givenCommand = new CreateUserCommand(givenFirstname, givenLastname, givenEmail, givenPassword);

        // Simulate an unexpected error from userQueryGateway
        given(userQueryGateway.handle(new FindUserByEmailQuery(givenEmail))).willThrow(new RuntimeException("Unexpected error"));

        // WHEN/THEN
        // Expect the runtime exception to propagate
        assertThatThrownBy(() -> useCase.handle(givenCommand))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Unexpected error");

        // Verify that no user was saved since an exception occurred
        then(storage).should(never()).save(any(UserAggregate.class));
    }

    @Test
    @DisplayName("GIVEN multiple commands with different new emails WHEN handle is called multiple times THEN each call creates a new user successfully")
    void testHandleMultipleCallsWithNewEmail() {
        // GIVEN
        // Two different emails that do not exist
        final String givenEmail1 = "multi.one@example.com";
        final String givenEmail2 = "multi.two@example.com";
        final String givenFirstname = "Multi";
        final String givenLastname = "User";
        final String givenPassword = "multiPass";

        final CreateUserCommand firstCommand = new CreateUserCommand(givenFirstname, givenLastname, givenEmail1, givenPassword);
        final CreateUserCommand secondCommand = new CreateUserCommand(givenFirstname, givenLastname, givenEmail2, givenPassword);

        // Both queries return empty (no existing user for these emails)
        given(userQueryGateway.handle(new FindUserByEmailQuery(givenEmail1))).willReturn(Optional.empty());
        given(userQueryGateway.handle(new FindUserByEmailQuery(givenEmail2))).willReturn(Optional.empty());

        // WHEN - first call
        final UserId firstResult = useCase.handle(firstCommand);

        // THEN - first call
        then(storage).should(times(1)).save(any(UserAggregate.class));
        assertThat(firstResult).isNotNull();

        // Reset mocks for a clean second call scenario
        reset(storage, userQueryGateway);
        given(userQueryGateway.handle(new FindUserByEmailQuery(givenEmail2))).willReturn(Optional.empty());

        // WHEN - second call
        final UserId secondResult = useCase.handle(secondCommand);

        // THEN - second call
        then(storage).should(times(1)).save(any(UserAggregate.class));
        assertThat(secondResult).isNotNull();
    }
}
