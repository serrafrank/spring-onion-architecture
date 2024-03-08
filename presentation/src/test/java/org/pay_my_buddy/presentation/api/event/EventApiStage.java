package org.pay_my_buddy.presentation.api.event;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.BeforeStage;
import com.tngtech.jgiven.integration.spring.JGivenStage;
import lombok.Getter;
import org.pay_my_buddy.entity.commun.api.event.Event;
import org.pay_my_buddy.entity.commun.api.event.EventHandler;
import org.pay_my_buddy.presentation.api.DefaultEventApi;
import org.pay_my_buddy.presentation.api.providers.EventHandlerProvider;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JGivenStage
public class EventApiStage extends Stage<EventApiStage> {

    private final List<EventHandler<?>> eventHandlers = new ArrayList<>();

    private DefaultEventApi defaultEventBus;

    private FirstEventHandlerImpl firstEventHandler = new FirstEventHandlerImpl();
    private SecondEventHandlerImpl secondEventHandler = new SecondEventHandlerImpl();


    private Event event = null;

    private Exception exception = null;

    @BeforeStage
    public void setUp() {
        eventHandlers.clear();
        resetBean();

        firstEventHandler = new FirstEventHandlerImpl();
        secondEventHandler = new SecondEventHandlerImpl();
        event = null;
        exception = null;
    }

    public EventApiStage a_event() {
        this.event = new TestEvent();
        return self();
    }


    public EventApiStage a_event_handler_for_the_event() {
        eventHandlers.add(firstEventHandler);
        resetBean();
        return self();
    }

    public EventApiStage two_events_handler_for_the_same_event() {
        eventHandlers.addAll(List.of(firstEventHandler, secondEventHandler));
        resetBean();
        return self();
    }


    public EventApiStage no_handler() {
        return self();
    }

    public EventApiStage execute_the_event() {
        try {
            defaultEventBus.publish(event);
        } catch (Exception e) {
            this.exception = e;
        }
        return self();
    }

    public EventApiStage the_event_is_executed() {
        assertThat(exception).isNull();
        assertThat(firstEventHandler.isExecuted()).isTrue();
        return self();
    }

    private void resetBean() {
        try {
            final EventHandlerProvider eventHandlerProvider = new EventHandlerProvider(eventHandlers);
            defaultEventBus = new DefaultEventApi(eventHandlerProvider);
        } catch (Exception e) {
            this.exception = e;
        }
    }

    public void an_exception_is_thrown() {
        assertThat(exception).isNotNull();
    }

    private static class TestEvent implements Event {
    }


    @Getter
    private static class FirstEventHandlerImpl implements EventHandler<TestEvent> {

        private boolean executed = false;

        @Override
        public void handle(TestEvent event) {
            executed = true;
        }

        public void reset() {
            executed = false;
        }
    }

    @Getter
    private static class SecondEventHandlerImpl implements EventHandler<TestEvent> {

        private boolean executed = false;

        @Override
        public void handle(TestEvent event) {
            executed = true;
        }

        public void reset() {
            executed = false;
        }
    }


}
