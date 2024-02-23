package com.example.paymybuddy.application.user.command;

import com.example.paymybuddy.application.user.UserService;
import com.example.paymybuddy.core.common.entity.CurrencyCode;
import com.example.paymybuddy.core.common.valueobject.Amount;
import com.example.paymybuddy.core.user.valueobject.UserId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class UserCommandImplTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserCommandImpl userCommand;


    @Test
    void shouldCreateUser() {
        final UserId userId = new UserId();
        final CreateUserCommand command = new CreateUserCommand("test@example.com", "Password1!", "John", "Doe");
        when(userService.createUser(anyString(), anyString(), anyString(), anyString())).thenReturn(userId);

        UserId id = userCommand.createUser(command);

        assertNotNull(id);
        assertEquals(userId, id);

        verify(userService, times(1)).createUser(anyString(), anyString(), anyString(), anyString());
    }

    private static String[] shouldThrowExceptionWhenNameIsNullParameters() {
        return new String[]{null, "", " ", "  "};
    }


    @ParameterizedTest
    @MethodSource("shouldThrowExceptionWhenNameIsNullParameters")
    void shouldThrowExceptionWhenLastNameIsNull(String lastname) {
        CreateUserCommand createUserCommand = new CreateUserCommand("email@email.com", "Password1!", "John", lastname);
        assertThrows(IllegalArgumentException.class, () -> userCommand.createUser(createUserCommand));
    }


    @ParameterizedTest
    @MethodSource("shouldThrowExceptionWhenNameIsNullParameters")
    void shouldThrowExceptionWhenFirstNameIsNull(String firstName) {
        CreateUserCommand createUserCommand = new CreateUserCommand("email@email.com", "Password1!", firstName, "Doe");
        assertThrows(IllegalArgumentException.class, () -> userCommand.createUser(createUserCommand));
    }



    @ParameterizedTest
    @ValueSource(strings = {"", "test", "test@", "test@example", "test@example."})
    void shouldThrowExceptionWhenEmailIsInvalid(String email) {
        CreateUserCommand createUserCommand = new CreateUserCommand(email, "Password1!", "John", "Doe");
        assertThrows(IllegalArgumentException.class, () -> userCommand.createUser(createUserCommand));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "password", "password1", "PASSWORD", "PASSWORD1", "password!", "PASSWORD!", "password1!", "PASSWORD1!"})
    void shouldThrowExceptionWhenPasswordIsInvalid(String password) {
        CreateUserCommand createUserCommand = new CreateUserCommand("test@example.com", password, "John", "Doe");
        assertThrows(IllegalArgumentException.class, () -> userCommand.createUser(createUserCommand));
    }

    @Test
    void shouldDebitUser() {
        final UserId userId = new UserId();
        final Amount amount = new Amount(BigDecimal.valueOf(100), CurrencyCode.EUR);
        final DebitUserCommand command = new DebitUserCommand(userId, amount);
        doNothing().when(userService).debitUser(any(UserId.class), any(Amount.class));

        userCommand.debitUser(command);

        verify(userService, times(1)).debitUser(any(UserId.class), any(Amount.class));
    }

    @Test
    void shouldCreditUse() {
        final UserId userId = new UserId();
        final Amount amount = new Amount(BigDecimal.valueOf(100), CurrencyCode.EUR);
        CreditUserCommand command = new CreditUserCommand(userId, amount);
        doNothing().when(userService).creditUser(any(UserId.class), any(Amount.class));

        userCommand.creditUser(command);

        verify(userService, times(1)).creditUser(any(UserId.class), any(Amount.class));
    }
}