package org.pay_my_buddy.presentation.api.providers;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.pay_my_buddy.application.common.api.*;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CommandHandlerProviderTest {


    private final ApplicationContext applicationContext = mock(ApplicationContext.class);
    private CommandHandlerProvider commandHandlerProvider;

    @Test
    @DisplayName("getHandler returns handler when command type matches")
    void getHandlerReturnsHandlerWhenCommandTypeMatches() {
        // Given
        MockCommand command = new MockCommand();
        CommandHandler<MockCommand, Void> handler = new MockCommandHandler();
        when(applicationContext.getBeansOfType(CommandHandler.class)).thenReturn(Map.of(handler.getClass().getName(), handler));
        commandHandlerProvider = new CommandHandlerProvider(applicationContext);


        // When
        Optional<CommandHandler<Command, Void>> result = commandHandlerProvider.getHandler(command);

        // Then
        assertTrue(result.isPresent());
        assertEquals(handler, result.get());
    }

    @Test
    @DisplayName("getHandler returns empty Optional when no handler found")
    void getHandlerReturnsEmptyOptionalWhenNoHandlerFound() {
        // Given
        MockCommand command = new MockCommand();
        when(applicationContext.getBeansOfType(CommandHandler.class)).thenReturn(new HashMap<>());
        commandHandlerProvider = new CommandHandlerProvider(applicationContext);

        // When
        Optional<CommandHandler<MockCommand, Void>> result = commandHandlerProvider.getHandler(command);

        // Then
        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("getHandler throws NullPointerException when command is null")
    void getHandlerThrowsNullPointerExceptionWhenCommandIsNull() {
        // Given
        when(applicationContext.getBeansOfType(CommandHandler.class)).thenReturn(new HashMap<>());

        // When & Then
        assertThrows(NullPointerException.class, () -> commandHandlerProvider.getHandler(null));
    }

    @Test
    @DisplayName("constructor throws DuplicateHandlerFoundException when duplicate handlers found")
    void constructorThrowsDuplicateHandlerFoundExceptionWhenDuplicateHandlersFound() {
        // Given
        CommandHandler<?, ?> handler1 = new MockCommandHandler();
        CommandHandler<?, ?> handler2 = new MockCommandHandler();
        when(applicationContext.getBeansOfType(CommandHandler.class)).thenReturn(Map.of("handler1", handler1, "handler2", handler2));

        // When & Then
        assertThrows(DuplicateHandlerFoundException.class, () -> new CommandHandlerProvider(applicationContext));
    }

    @Test
    @DisplayName("constructor initializes without duplicates")
    void constructorInitializesWithoutDuplicates() {
        // Given
        when(applicationContext.getBeansOfType(CommandHandler.class)).thenReturn(new HashMap<>());

        // When & Then
        assertDoesNotThrow(() -> new CommandHandlerProvider(applicationContext));
    }


    static class MockCommand extends AbstractCommand<Void> {
    }

    private static class MockCommandHandler implements CommandHandler<MockCommand, Void> {
        @Override
        public CommandResponse<Void> handle(MockCommand command) {
            return CommandResponse.empty();
        }
    }
}