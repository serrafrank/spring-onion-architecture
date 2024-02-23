package com.example.paymybuddy.application.user;

import com.example.paymybuddy.core.common.entity.CurrencyCode;
import com.example.paymybuddy.core.common.entity.id.Id;
import com.example.paymybuddy.core.common.valueobject.Amount;
import com.example.paymybuddy.core.user.UserAggregate;
import com.example.paymybuddy.core.user.valueobject.UserId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    private Id id;
    private UserAggregate userAggregate;

    @BeforeEach
    void setUp() {
        id = new UserId();
        userAggregate = new UserAggregate("John", "Doe", "john.doe@example.com", "password123");
    }

    @Test
    void shouldFindUser() {
        when(userRepository.findUser(id)).thenReturn(Optional.of(userAggregate));
        Optional<UserAggregate> foundUser = userService.findUser(id);
        assertTrue(foundUser.isPresent());
        assertEquals(userAggregate, foundUser.get());
    }

    @Test
    void shouldNotFindUser() {
        when(userRepository.findUser(id)).thenReturn(Optional.empty());
        Optional<UserAggregate> foundUser = userService.findUser(id);
        assertFalse(foundUser.isPresent());
    }

    @Test
    void shouldCreateUser() {
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userRepository.saveUser(any(UserAggregate.class))).thenReturn(new UserId());
        UserId userId = userService.createUser("John", "Doe", "john.doe@example.com", "password123");
        assertNotNull(userId);
    }

    @Test
    void shouldNotCreateUserWhenEmailExists() {
        when(userRepository.existsByEmail(anyString())).thenReturn(true);
        assertThrows(IllegalArgumentException.class, () -> userService.createUser("John", "Doe", "john.doe@example.com", "password123"));
    }

    @Test
    void shouldDebitUser() {
        userAggregate.getAccount().credit(new Amount(BigDecimal.valueOf(100), CurrencyCode.EUR));

        when(userRepository.findUser(id)).thenReturn(Optional.of(userAggregate));
        userService.debitUser(id, new Amount(BigDecimal.valueOf(100), CurrencyCode.EUR));
        verify(userRepository, times(1)).saveUser(userAggregate);
    }

    @Test
    void shouldThrowExceptionWhenDebitAmountIsGreaterThanBalance() {
        when(userRepository.findUser(id)).thenReturn(Optional.of(userAggregate));
        Executable executable = () -> userService.debitUser(id, new Amount(BigDecimal.valueOf(100), CurrencyCode.EUR));
        assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    void shouldCreditUser() {
        when(userRepository.findUser(id)).thenReturn(Optional.of(userAggregate));
        userService.creditUser(id, new Amount(BigDecimal.valueOf(100), CurrencyCode.EUR));
        verify(userRepository, times(1)).saveUser(userAggregate);
    }

    @Test
    void shouldCheckIfUserExistsById() {
        when(userRepository.existsById(id)).thenReturn(true);
        assertTrue(userService.existsUser(id));
    }

    @Test
    void shouldCheckIfUserExistsByEmail() {
        when(userRepository.existsByEmail(anyString())).thenReturn(true);
        assertTrue(userService.existsUser("john.doe@example.com"));
    }

    @Test
    void shouldCheckIfUsersAreFriends() {
        when(userRepository.findUser(id)).thenReturn(Optional.of(userAggregate));
        assertFalse(userService.areFriends(id, new UserId()));
    }
}