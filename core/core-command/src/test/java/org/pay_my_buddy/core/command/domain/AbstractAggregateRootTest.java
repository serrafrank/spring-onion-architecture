package org.pay_my_buddy.core.command.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.pay_my_buddy.core.command.domain.event_storage.AggregateEventListener;
import org.pay_my_buddy.core.command.domain.event_storage.EventWrapper;
import org.pay_my_buddy.core.framework.domain.exception.BusinessException;
import org.pay_my_buddy.core.framework.domain.exception.ConflictException;
import org.pay_my_buddy.core.framework.domain.exception.SystemException;
import org.pay_my_buddy.core.framework.domain.message.Event;
import org.pay_my_buddy.core.framework.domain.message.EventId;
import org.pay_my_buddy.core.framework.domain.value_object.EntityId;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Unit tests for AbstractAggregateRoot")
class AbstractAggregateRootTest {

    /**
     * Test-specific entity ID class.
     */
    record TestEntityId(String value) implements EntityId {

        private final static String PREFIX = "TEST_ENTITY_";

        public TestEntityId {
            EntityId.validate(value, PREFIX);
        }

        public TestEntityId() {
            this(PREFIX + EntityId.generateId());
        }
    }

    /**
     * Creation event for testing.
     */
    static final class TestCreatedEvent implements Event {
        private final EventId eventId;
        private final String data;

        public TestCreatedEvent(final String data) {
            this.eventId = new EventId();
            this.data = data;
        }

        @Override
        public EventId eventId() {
            return eventId;
        }

        public String data() {
            return data;
        }
    }

    /**
     * Update event for testing.
     */
    static final class TestUpdatedEvent implements Event {
        private final EventId eventId;
        private final String newData;

        public TestUpdatedEvent(final String newData) {
            this.eventId = new EventId();
            this.newData = newData;
        }

        @Override
        public EventId eventId() {
            return eventId;
        }

        public String newData() {
            return newData;
        }
    }

    /**
     * Event with no handler, for testing the 'no handler' scenario.
     */
    static final class TestNoHandlerEvent implements Event {
        private final EventId eventId;

        public TestNoHandlerEvent() {
            this.eventId = new EventId();
        }

        @Override
        public EventId eventId() {
            return eventId;
        }
    }

    // Classes moved here as requested:

    /**
     * Event with multiple handlers scenario.
     */
    static final class TestMultipleHandlerEvent implements Event {
        private final EventId eventId;

        public TestMultipleHandlerEvent() {
            this.eventId = new EventId();
        }

        @Override
        public EventId eventId() {
            return eventId;
        }
    }

    /**
     * TestAggregate that extends AbstractAggregateRoot for testing purposes.
     */
    static class TestAggregate extends AbstractAggregateRoot<TestAggregate, TestEntityId> {

        private String data;
        private boolean snapshotCreated;

        protected TestAggregate(TestEntityId id) {
            super(id);
        }

        public static TestAggregate newInstance() {
            return new TestAggregate(new TestEntityId());
        }

        public void create(final String initialData) {
            addEvent(new TestCreatedEvent(initialData));
        }

        public void update(final String updatedData) {
            addEvent(new TestUpdatedEvent(updatedData));
        }

        public void createSnapshotForTest() {
            createSnapshot();
        }

        @AggregateEventListener
        public void on(TestCreatedEvent event) {
            this.data = event.data();
        }

        @AggregateEventListener
        public void on(TestUpdatedEvent event) {
            this.data = event.newData();
        }

        @AggregateEventListener
        public void on(TestMultipleHandlerEvent event) {
            // First handler for TestMultipleHandlerEvent
        }

        @AggregateEventListener
        public void on(CreateSnapshotAggregateEvent<TestAggregate> event) {
            this.snapshotCreated = true;
            this.data = event.aggregate().data;
        }

        public boolean isSnapshotCreated() {
            return snapshotCreated;
        }

        @Override
        protected void resetAggregate() {
            this.data = null;
        }

        @Override
        protected TestAggregate clone() {
            var clone = new TestAggregate(id());
            clone.data = this.data;
            clone.snapshotCreated = this.snapshotCreated;
            return clone;
        }
    }

    @Nested
    @DisplayName("GIVEN a new TestAggregate instance")
    class NewAggregateTests {

        private TestAggregate aggregate;

        @BeforeEach
        void setUp() {
            // GIVEN: A new aggregate instance
            this.aggregate = TestAggregate.newInstance();
        }

        @Test
        @DisplayName("WHEN getting the aggregate version THEN it should be 0 initially")
        void testVersionInitiallyZero() {
            // WHEN
            final int version = aggregate.version();

            // THEN
            assertThat(version).isEqualTo(0);
        }

        @Test
        @DisplayName("WHEN adding an event THEN uncommitedChanges contain the event and version increases")
        void testAddEvent() {
            // GIVEN
            final String givenData = "initialData";

            // WHEN
            aggregate.create(givenData);

            // THEN
            assertThat(aggregate.uncommitedChanges()).hasSize(1);
            assertThat(aggregate.version()).isEqualTo(1);
            assertThat(aggregate.commitedChanges()).isEmpty();
        }

        @Test
        @DisplayName("WHEN committing changes THEN uncommitedChanges is cleared and commitedChanges contain the event")
        void testCommitChanges() {
            // GIVEN
            final String givenData = "initialData";
            aggregate.create(givenData);

            // WHEN
            aggregate.commitChanges();

            // THEN
            assertThat(aggregate.uncommitedChanges()).isEmpty();
            assertThat(aggregate.commitedChanges()).hasSize(1);
        }

        @Test
        @DisplayName("WHEN rolling back changes with no commit THEN uncommitedChanges is cleared and state is initial")
        void testRollbackChangesNoCommits() {
            // GIVEN
            final String givenData = "initialData";
            aggregate.create(givenData);
            assertThat(aggregate.data).isEqualTo(givenData);

            // WHEN
            aggregate.rollbackChanges();

            // THEN
            assertThat(aggregate.uncommitedChanges()).isEmpty();
            assertThat(aggregate.commitedChanges()).isEmpty();
            // State should return to initial
            assertThat(aggregate.data).isNull();
        }

        @Test
        @DisplayName("WHEN rolling back after a commit THEN state is restored by replaying committed events")
        void testRollbackAfterCommit() {
            // GIVEN
            final String givenData = "initialData";
            aggregate.create(givenData);
            aggregate.commitChanges();
            final String secondData = "updatedData";
            aggregate.update(secondData);
            assertThat(aggregate.data).isEqualTo(secondData);

            // WHEN
            aggregate.rollbackChanges();

            // THEN
            // Only the committed event (TestCreatedEvent) is replayed
            assertThat(aggregate.uncommitedChanges()).isEmpty();
            assertThat(aggregate.commitedChanges()).hasSize(1);
            assertThat(aggregate.data).isEqualTo(givenData);
        }

        @Test
        @DisplayName("WHEN creating a snapshot THEN a snapshot event is added to uncommitedChanges")
        void testCreateSnapshot() {
            // GIVEN
            final String givenData = "snapshotData";
            aggregate.create(givenData);

            // WHEN
            aggregate.createSnapshotForTest();

            // THEN
            assertThat(aggregate.uncommitedChanges()).hasSize(2);
            assertThat(aggregate.isSnapshotCreated()).isTrue();
        }

        @Test
        @DisplayName("WHEN recreating the aggregate from a list of events THEN the aggregate state matches the final state")
        void testRecreateAggregate() {
            // GIVEN
            final String givenData = "initialData";
            aggregate.create(givenData);
            aggregate.commitChanges();

            final String updatedData = "dataAfterUpdate";
            aggregate.update(updatedData);
            aggregate.commitChanges();

            final List<EventWrapper> givenEvents = new ArrayList<>(aggregate.commitedChanges());
            final TestAggregate newAggregate = TestAggregate.newInstance();

            // WHEN
            newAggregate.recreateAggregate(givenEvents);

            // THEN
            assertThat(newAggregate.data).isEqualTo(updatedData);
            assertThat(newAggregate.version()).isEqualTo(2);
        }

        @Test
        @DisplayName("WHEN replaying events THEN state is correctly updated")
        void testReplayEvents() {
            // GIVEN
            final String givenData = "initialData";
            aggregate.create(givenData);
            aggregate.commitChanges();

            // Manually altering data without an event
            aggregate.data = "wrongData";
            assertThat(aggregate.data).isEqualTo("wrongData");

            // WHEN
            aggregate.replayEvents();

            // THEN
            // After replay, it should return to initialData
            assertThat(aggregate.data).isEqualTo(givenData);
        }
    }

    @Nested
    @DisplayName("GIVEN a TestAggregate with event handlers")
    class HandlersTests {

        private TestAggregate aggregate;

        @BeforeEach
        void setUp() {
            this.aggregate = TestAggregate.newInstance();
        }

        @Test
        @DisplayName("WHEN adding an event without any handler THEN a BusinessException is thrown")
        void testAddEventWithoutHandler() {
            // GIVEN
            final TestNoHandlerEvent givenEvent = new TestNoHandlerEvent();

            // WHEN/THEN
            assertThatThrownBy(() -> aggregate.addEvent(givenEvent))
                    .isInstanceOf(BusinessException.class)
                    .hasMessageContaining("No handler registered for event type");
        }

        @Test
        @DisplayName("WHEN adding an event with multiple handlers THEN a BusinessException is thrown")
        void testAddEventWithMultipleHandlers() {
            // GIVEN
            class MultipleHandlerAggregate extends TestAggregate {
                protected MultipleHandlerAggregate(TestEntityId id) {
                    super(id);
                }

                @AggregateEventListener
                public void onHandler(TestMultipleHandlerEvent event) {
                    // second handler
                }
            }

            final MultipleHandlerAggregate givenAggregate = new MultipleHandlerAggregate(new TestEntityId());
            final TestMultipleHandlerEvent givenEvent = new TestMultipleHandlerEvent();

            // WHEN/THEN
            assertThatThrownBy(() -> givenAggregate.addEvent(givenEvent))
                    .isInstanceOf(BusinessException.class);
        }

        @Test
        @DisplayName("WHEN a handler throws an exception THEN it is wrapped in a BusinessException")
        void testHandlerThrowsException() {
            // GIVEN
            class ExceptionThrowingAggregate extends AbstractAggregateRoot<ExceptionThrowingAggregate, TestEntityId> {
                protected ExceptionThrowingAggregate(TestEntityId id) {
                    super(id);
                }

                @AggregateEventListener
                public void on(TestCreatedEvent event) {
                    throw new ConflictException("Simulated conflict");
                }

                @Override
                public void on(CreateSnapshotAggregateEvent<ExceptionThrowingAggregate> event) {
                    // no-op
                }

                @Override
                protected void resetAggregate() {
                    // no-op
                }

                @Override
                protected ExceptionThrowingAggregate clone() {
                    return new ExceptionThrowingAggregate(id());
                }
            }

            final ExceptionThrowingAggregate givenAggregate = new ExceptionThrowingAggregate(new TestEntityId());
            final TestCreatedEvent givenEvent = new TestCreatedEvent("data");

            // WHEN/THEN
            assertThatThrownBy(() -> givenAggregate.addEvent(givenEvent))
                    .isInstanceOf(SystemException.class)
                    .hasCauseInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Error applying event");
        }
    }

    @Nested
    @DisplayName("GIVEN a TestAggregate and a list of events including a snapshot")
    class SnapshotRecreateTests {

        private TestAggregate aggregate;

        @BeforeEach
        void setUp() {
            this.aggregate = TestAggregate.newInstance();
        }

        @Test
        @DisplayName("WHEN recreating the aggregate from a list of events with a snapshot THEN final state matches snapshot + subsequent events")
        void testRecreateAggregateWithSnapshot() {
            // GIVEN
            final String initialData = "initial";
            aggregate.create(initialData);
            aggregate.commitChanges();

            final String updatedDataBeforeSnapshot = "updatedBeforeSnapshot";
            aggregate.update(updatedDataBeforeSnapshot);
            aggregate.commitChanges();

            // Create snapshot
            aggregate.createSnapshotForTest();
            aggregate.commitChanges();

            final String updatedDataAfterSnapshot = "updatedAfterSnapshot";
            aggregate.update(updatedDataAfterSnapshot);
            aggregate.commitChanges();

            final List<EventWrapper> givenEvents = new ArrayList<>(aggregate.commitedChanges());
            final TestAggregate newAggregate = TestAggregate.newInstance();

            // WHEN
            newAggregate.recreateAggregate(givenEvents);

            // THEN
            // The aggregate should have the state after snapshot plus the subsequent updates
            assertThat(newAggregate.data).isEqualTo(updatedDataAfterSnapshot);
            assertThat(newAggregate.version()).isEqualTo(givenEvents.size());
            assertThat(newAggregate.isSnapshotCreated()).isTrue();
        }
    }

    @Nested
    @DisplayName("GIVEN aggregates with complex scenarios")
    class ComplexScenarioTests {

        @Test
        @DisplayName("WHEN no events are present THEN version is 0")
        void testNoEventsVersionIsZero() {
            // GIVEN
            final TestAggregate givenAggregate = TestAggregate.newInstance();

            // WHEN
            final int version = givenAggregate.version();

            // THEN
            assertThat(version).isZero();
        }

        @Test
        @DisplayName("WHEN recreateAggregate is called with an empty event list THEN the state remains initial")
        void testRecreateWithNoEvents() {
            // GIVEN
            final TestAggregate givenAggregate = TestAggregate.newInstance();
            final List<EventWrapper> noEvents = Collections.emptyList();

            // WHEN
            givenAggregate.recreateAggregate(noEvents);

            // THEN
            assertThat(givenAggregate.version()).isZero();
            assertThat(givenAggregate.data).isNull();
        }

        @Test
        @DisplayName("WHEN calling version after multiple addEvent without commit THEN version includes both committed and uncommitted events")
        void testVersionCountBothUncommittedAndCommitted() {
            // GIVEN
            final TestAggregate givenAggregate = TestAggregate.newInstance();
            givenAggregate.create("first");
            givenAggregate.commitChanges();
            givenAggregate.update("second"); // not committed

            // WHEN
            final int version = givenAggregate.version();

            // THEN
            // 1 committed + 1 uncommitted = 2
            assertThat(version).isEqualTo(2);
        }

        @Test
        @DisplayName("WHEN adding event with an internal handler THEN the event is applied immediately")
        void testApplyChangesImmediately() {
            // GIVEN
            final TestAggregate givenAggregate = TestAggregate.newInstance();
            final String dataGiven = "immediateData";
            final TestCreatedEvent givenEvent = new TestCreatedEvent(dataGiven);

            // WHEN
            givenAggregate.addEvent(givenEvent);

            // THEN
            assertThat(givenAggregate.data).isEqualTo(dataGiven);
            assertThat(givenAggregate.uncommitedChanges()).hasSize(1);
        }
    }
}
