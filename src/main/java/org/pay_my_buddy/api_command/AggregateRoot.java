package org.pay_my_buddy.api_command;

import lombok.extern.slf4j.Slf4j;
import org.pay_my_buddy.api_command.event_storage.AggregateEventHandler;
import org.pay_my_buddy.shared.EntityId;

import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Consumer;

@Slf4j
public abstract class AggregateRoot<AGGREGATE extends AggregateRoot<AGGREGATE, ID>, ID extends EntityId> {
	private final ID id;
	private int version = 0;

	private final List<Event> changes = new ArrayList<>();

	private final Map<Class<? extends Event>, Consumer<? extends Event>> eventHandler = new HashMap<>();

	protected AggregateRoot(ID id) {
		this.id = id;
		registerEventHandlers();
	}

	public ID id() {
		return this.id;
	}

	public int version() {
		return this.version;
	}

	public void version(int version) {
		this.version = version;
	}

	public List<Event> uncommittedChanges() {
		return this.changes;
	}

	public void commitChanges() {
		this.changes.clear();
	}

	public <EVENT extends Event> AGGREGATE addEvent(EVENT event) {
		applyChanges(event);
		changes.add(event);
		return (AGGREGATE) this;
	}

	public void replayEvents(Iterable<Event> events) {
		events.forEach(this::applyChanges);
	}

	protected <EVENT extends Event> void applyChanges(EVENT event) {
		final Consumer<EVENT> handler = (Consumer<EVENT>) eventHandler.get(event.getClass());
		if (handler == null) {
			throw new IllegalArgumentException("No handler registered for event type: " + event.getClass());
		}
		handler.accept(event);
	}

	protected <EVENT extends Event> void registerEventHandler(Class<EVENT> eventType, Consumer<EVENT> handler) {
		if (eventHandler.containsKey(eventType)) {
			throw new IllegalArgumentException("Handler already registered for event type: " + eventType);
		}
		eventHandler.put(eventType, handler);
	}

	private void registerEventHandlers() {
		Arrays.stream(this.getClass().getDeclaredMethods())
				.filter(method -> method.isAnnotationPresent(AggregateEventHandler.class))
				.forEach(this::invokeEventMethodHandler);

		log.info("{} event handlers registered for aggregate {}", eventHandler.size(), this.getClass().getSimpleName());
	}

	private void invokeEventMethodHandler(Method method) {
		final Class<?> eventType = method.getParameterTypes()[0];
		registerEventHandler((Class<Event>) eventType, event -> {
			try {
				method.invoke(this, event);
			} catch (Exception e) {
				log.error("Error applying event", e);
			}
		});
	}
}

