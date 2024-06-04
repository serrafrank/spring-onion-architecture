package org.pay_my_buddy.presentation.api;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.pay_my_buddy.application.common.api.event.Event;
import org.pay_my_buddy.application.common.api.event.EventHandler;
import org.pay_my_buddy.presentation.api.providers.EventHandlerProvider;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DefaultEventApiTest {


    // Verify that publish method dispatches an event to all appropriate handlers
    @Test
    @DisplayName("Verify that publish method dispatches an event to all appropriate handlers")
    void dispatch_event_to_all_handlers() {
        // Given
        Event mockEvent = mock(Event.class);
        EventHandler<Event> mockHandler1 = mock(EventHandler.class);
        EventHandler<Event> mockHandler2 = mock(EventHandler.class);
        Set<EventHandler<Event>> handlers = new HashSet<>(Arrays.asList(mockHandler1, mockHandler2));
        EventHandlerProvider mockProvider = mock(EventHandlerProvider.class);
        when(mockProvider.getHandlers(mockEvent)).thenReturn(handlers);
        DefaultEventApi eventApi = new DefaultEventApi(mockProvider);

        // When
        eventApi.publish(mockEvent);

        // Then
        verify(mockHandler1, times(1)).handle(mockEvent);
        verify(mockHandler2, times(1)).handle(mockEvent);
    }

    // Check that all handlers for a specific event type are called once
    @Test
    @DisplayName("Check that all handlers for a specific event type are called once")
    void ensure_all_handlers_called_once() {
        // Given
        Event specificEvent = mock(Event.class);
        EventHandler<Event> handler = mock(EventHandler.class);
        Set<EventHandler<Event>> singleHandlerSet = Collections.singleton(handler);
        EventHandlerProvider provider = mock(EventHandlerProvider.class);
        when(provider.getHandlers(specificEvent)).thenReturn(singleHandlerSet);
        DefaultEventApi api = new DefaultEventApi(provider);

        // When
        api.publish(specificEvent);

        // Then
        verify(handler, times(1)).handle(specificEvent);
    }

    // Ensure that event handlers are called with the correct event instance
    @Test
    @DisplayName("Ensure that event handlers are called with the correct event instance")
    void handlers_receive_correct_event_instance() {
        // Given
        Event expectedEvent = mock(Event.class);
        EventHandler<Event> eventHandler = mock(EventHandler.class);
        Set<EventHandler<Event>> handlers = Collections.singleton(eventHandler);
        EventHandlerProvider provider = mock(EventHandlerProvider.class);
        when(provider.getHandlers(expectedEvent)).thenReturn(handlers);
        DefaultEventApi api = new DefaultEventApi(provider);

        // When
        api.publish(expectedEvent);

        // Then
        verify(eventHandler).handle(expectedEvent);
    }

    // Handle scenarios where an event handler throws an exception
    @Test
    @DisplayName("Handle scenarios where an event handler throws an exception")
    void handle_exceptions_in_event_handlers() {
        // Given
        Event problematicEvent = mock(Event.class);
        EventHandler<Event> problematicHandler = mock(EventHandler.class);
        doThrow(new RuntimeException("Handler error")).when(problematicHandler).handle(problematicEvent);
        Set<EventHandler<Event>> handlersWithException = Collections.singleton(problematicHandler);
        EventHandlerProvider provider = mock(EventHandlerProvider.class);
        when(provider.getHandlers(problematicEvent)).thenReturn(handlersWithException);
        DefaultEventApi api = new DefaultEventApi(provider);

        // When & Then
        assertThrows(RuntimeException.class, () -> api.publish(problematicEvent));
    }

    // Assess performance implications when handling a large number of events
    @Test
    @DisplayName("Assess performance implications when handling a large number of events")
    void performance_with_large_number_of_events() {
        // Given
        int numberOfEvents = 10000;
        Event commonEvent = mock(Event.class);
        EventHandler<Event> commonHandler = mock(EventHandler.class);
        Set<EventHandler<Event>> manyHandlers = Stream.generate(() -> commonHandler).limit(numberOfEvents).collect(Collectors.toSet());

        EventHandlerProvider provider = mock(EventHandlerProvider.class);
        when(provider.getHandlers(commonEvent)).thenReturn(manyHandlers);

        DefaultEventApi api = new DefaultEventApi(provider);

        // When & Then
        assertTimeoutPreemptively(Duration.ofSeconds(5), () -> api.publish(commonEvent), "Publishing takes too long with many events");
    }

    // Evaluate system behavior under concurrent event publishing
    @Test
    @DisplayName("Evaluate system behavior under concurrent event publishing")
    void concurrent_event_publishing_behavior() throws InterruptedException {
        // Given
        Event concurrentEvent = mock(Event.class);

        EventHandler<Event> handler1 = mock(EventHandler.class);
        EventHandler<Event> handler2 = mock(EventHandler.class);

        Set<EventHandler<Event>> concurrentHandlers = new HashSet<>(Arrays.asList(handler1, handler2));

        EventHandlerProvider provider = mock(EventHandlerProvider.class);

        when(provider.getHandlers(concurrentEvent)).thenReturn(concurrentHandlers);

        DefaultEventApi api = new DefaultEventApi(provider);

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        // When & Then
        IntStream.range(0, 100).forEach(i -> executorService.submit(() -> api.publish(concurrentEvent)));
        executorService.shutdown();
        assertTrue(executorService.awaitTermination(1, TimeUnit.MINUTES), "Failed to complete in time");
        verify(handler1, atLeastOnce()).handle(concurrentEvent);
        verify(handler2, atLeastOnce()).handle(concurrentEvent);
    }

}