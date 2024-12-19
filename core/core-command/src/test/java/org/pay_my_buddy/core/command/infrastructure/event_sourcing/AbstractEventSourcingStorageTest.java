package org.pay_my_buddy.core.command.infrastructure.event_sourcing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.pay_my_buddy.core.command.domain.AbstractAggregateRoot;
import org.pay_my_buddy.core.command.domain.event_storage.*;
import org.pay_my_buddy.core.framework.domain.exception.BusinessException;
import org.pay_my_buddy.core.framework.domain.exception.InternalErrorException;
import org.pay_my_buddy.core.framework.domain.exception.SystemException;
import org.pay_my_buddy.core.framework.domain.message.Event;
import org.pay_my_buddy.core.framework.domain.message.EventId;
import org.pay_my_buddy.core.framework.domain.value_object.EntityId;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

/**
 * Unit tests for AbstractEventSourcingStorage
 * <p>
 * In this test, we create a concrete subclass of AbstractEventSourcingStorage
 * to be able to test its behavior.
 * <p>
 * We use a test aggregate and test events to simulate real usage.
 * <p>
 * GIVEN-WHEN-THEN convention:
 * - GIVEN: setup initial state, mocks, test data
 * - WHEN: invoke the method under test
 * - THEN: verify results, thrown exceptions, side effects
 */
class AbstractEventSourcingStorageTest {

    /**
     * Mocked classes and test instance
     */
    private EventSourcingRepositoryTest repository;
    private MessagePublisher publisher;
    private EventSourcingStorage<TestAggregate, TestEntityId> storage;

    @BeforeEach
    void setup() {
        repository = mock(EventSourcingRepositoryTest.class);
        publisher = mock(MessagePublisher.class);
        storage = new TestEventSourcingStorage(repository, publisher);
    }

    // Helper method to create a mock EventWrapper
    private EventWrapper createEventWrapper(EntityId aggregateId, Event event, int index) {
        return new EventWrapper() {
            @Override
            public EventId eventId() {
                return event.eventId();
            }

            @Override
            public java.time.LocalDateTime timestamp() {
                return java.time.LocalDateTime.now();
            }

            @Override
            public int index() {
                return index;
            }

            @Override
            public EntityId aggregateId() {
                return aggregateId;
            }

            @Override
            public String aggregateType() {
                return "TestAggregate";
            }

            @Override
            public String eventType() {
                return event.getClass().getName();
            }

            @Override
            public Event event() {
                return event;
            }
        };
    }


    /**
     * A repository interface extension for testing.
     */
    interface EventSourcingRepositoryTest extends EventSourcingRepository {
        // no additional methods required for tests
    }

    /**
     * A simple EntityId subclass for testing.
     */
    public record TestEntityId(String value) implements EntityId {

        private final static String PREFIX = "TEST_ENTITY_";

        public TestEntityId {
            EntityId.validate(value, PREFIX);
        }

        public TestEntityId() {
            this(PREFIX + EntityId.generateId());
        }
    }

    /**
     * A simple Event subclass for testing (using a record).
     */
    public record TestEvent(EventId eventId, String data) implements Event {
        public TestEvent(String data) {
            this(new EventId(), data);
        }
    }

    /**
     * A test aggregate extending AbstractAggregateRoot, for testing purposes.
     * It has a constructor accepting an ID, which matches the pattern expected by newInstance().
     */
    public static class TestAggregate extends AbstractAggregateRoot<TestAggregate.TestAggregateData, TestEntityId> {

        static class TestAggregateData{
            private String state = "initial";
        }

        protected TestAggregate(TestEntityId id) {
            super(id, new TestAggregateData());
        }

        public void applyChange(String newState) {
            addEvent(new TestEvent(newState));
        }

        @AggregateEventListener
        public void on(CreateSnapshotAggregateEvent<TestAggregateData> event) {
            // Implement snapshot logic if needed. Here we just copy the state.
            this.data().state = event.aggregate().state;
        }

        @Override
        protected void resetAggregate() {
            // Reset all fields to their initial state here
            this.data().state = "initial";
        }


        @AggregateEventListener
        public void on(TestEvent event) {
            this.data().state = event.data();
        }

        public String getState() {
            return this.data().state;
        }
    }

    /**
     * A concrete subclass of AbstractEventSourcingStorage for testing.
     */
    static class TestEventSourcingStorage extends AbstractEventSourcingStorage<TestAggregate, TestEntityId> {

        public TestEventSourcingStorage(EventSourcingRepositoryTest repository, MessagePublisher publisher) {
            super(repository, publisher);
        }
    }

    @Nested
    @DisplayName("GIVEN an aggregate with uncommitted events")
    class SaveTests {

        @Test
        @DisplayName("WHEN we save the aggregate THEN events are published, committed, a snapshot is created and persisted")
        void testSaveAggregate() {
            // GIVEN
            final TestAggregate givenAggregate = new TestAggregate(new TestEntityId());
            final String givenData = "newState";
            givenAggregate.applyChange(givenData);

            // No repository interaction before save
            reset(repository);

            // WHEN
            final TestAggregate result = storage.save(givenAggregate);

            // THEN
            // Verify that events were published
            verify(publisher, times(1)).publish(any(Event.class));
            // The aggregate should have created a snapshot and committed changes
            assertThat(result.uncommitedChanges()).isEmpty();
            assertThat(result.commitedChanges()).isNotEmpty();

            // Verify repository.saveAll was called
            verify(repository, times(1)).saveAll(assertArg(theList -> assertThat(theList)
                    //inside here theList has the correct type
                    .isNotNull()
                    .isNotEmpty()
            ));

            // The returned aggregate is the same instance
            assertThat(result).isSameAs(givenAggregate);
        }
    }

    @Nested
    @DisplayName("GIVEN an empty repository")
    class GetByIdTests {

        @Test
        @DisplayName("WHEN getById is called with no events found THEN BusinessException(AggregateNotFoundException) is thrown")
        void testGetByIdNoEvents() {
            // GIVEN
            final TestEntityId givenId = new TestEntityId();
            when(repository.findAllEventsAfterLastSnapshot(givenId.value())).thenReturn(List.of());

            // WHEN/THEN
            assertThatThrownBy(() -> storage.getById(givenId))
                    .isInstanceOf(BusinessException.class)
                    .hasCauseInstanceOf(AggregateNotFoundException.class);
        }

        @Test
        @DisplayName("WHEN getById is called with events found THEN aggregate is reconstructed")
        void testGetByIdWithEvents() {
            // GIVEN
            final TestEntityId givenId = new TestEntityId();
            final TestEvent event1 = new TestEvent("state1");
            final TestEvent event2 = new TestEvent("state2");

            final List<EventWrapper> eventWrappers = new ArrayList<>();
            eventWrappers.add(createEventWrapper(givenId, event1, 0));
            eventWrappers.add(createEventWrapper(givenId, event2, 1));

            when(repository.findAllEventsAfterLastSnapshot(givenId.value())).thenReturn(eventWrappers);

            // WHEN
            final TestAggregate aggregate = storage.getById(givenId);

            // THEN
            // The aggregate should have replayed events and final state should be 'state2'
            assertThat(aggregate.getState()).isEqualTo("state2");
        }
    }

    @Nested
    @DisplayName("GIVEN a repository with existing events")
    class RepublishEventsTests {

        @Test
        @DisplayName("WHEN republishEvents is called THEN all events are published in order")
        void testRepublishEvents() {
            // GIVEN
            final TestEntityId givenId = new TestEntityId();
            final TestEvent event1 = new TestEvent("state1");
            final TestEvent event2 = new TestEvent("state2");

            final List<EventWrapperEntity> allEvents = List.of(
                    new EventWrapperEntity(createEventWrapper(givenId, event1, 0)),
                    new EventWrapperEntity(createEventWrapper(givenId, event2, 1))
            );

            given(repository.findAll()).willReturn(allEvents);

            // WHEN
            storage.republishEvents();

            // THEN
            // Events should be published in order
            verify(publisher, times(2)).publish(any(Event.class));
            // We can check the order by argument capturing if needed
            ArgumentCaptor<Event> captor = ArgumentCaptor.forClass(Event.class);
            verify(publisher, times(2)).publish(captor.capture());
            List<Event> published = captor.getAllValues();

            assertThat(published).hasSize(2);
            assertThat(((TestEvent) published.get(0)).data()).isEqualTo("state1");
            assertThat(((TestEvent) published.get(1)).data()).isEqualTo("state2");
        }
    }

    @Nested
    @DisplayName("GIVEN abnormal scenarios")
    class ErrorScenarios {

        @Test
        @DisplayName("WHEN there's no suitable constructor THEN a SystemException is thrown")
        void testNoSuitableConstructor() {
            // GIVEN
            // We create a test storage that tries to instantiate a type without suitable constructor
            class NoConstructorAggregate extends AbstractAggregateRoot<NoConstructorAggregate.NoConstructorAggregateData, TestEntityId> {

                static class NoConstructorAggregateData {
                }

                protected NoConstructorAggregate() {
                    super(new TestEntityId(), new NoConstructorAggregateData());
                }

                @AggregateEventListener
                public void on(CreateSnapshotAggregateEvent<NoConstructorAggregateData> event) {
                    // no-op
                }

                @Override
                protected void resetAggregate() {
                    // no-op
                }
            }

            abstract class BrokenEventSourcingStorage extends AbstractEventSourcingStorage<NoConstructorAggregate, TestEntityId> {
                public BrokenEventSourcingStorage(EventSourcingRepositoryTest repository, MessagePublisher publisher) {
                    super(repository, publisher);
                }
            }

            EventSourcingStorage<NoConstructorAggregate, TestEntityId> brokenStorage = new BrokenEventSourcingStorage(repository, publisher) {
            };

            // WHEN/THEN
            // getById calls newInstance internally, expecting a single-arg constructor with correct type
            when(repository.findAllEventsAfterLastSnapshot(any())).thenReturn(List.of(createEventWrapper(new TestEntityId(), new TestEvent("data"), 0)));

            assertThatThrownBy(() -> brokenStorage.getById(new TestEntityId()))
                    .isInstanceOf(SystemException.class)
                    .hasCauseInstanceOf(InternalErrorException.class);
        }
    }
}
