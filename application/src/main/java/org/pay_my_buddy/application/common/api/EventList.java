package org.pay_my_buddy.application.common.api;

import java.util.LinkedList;
import java.util.List;

public final class EventList {

    private final List<Event> events = new LinkedList<>();

    private EventList() {
    }

    private EventList(List<Event> events) {
        this.events.addAll(List.copyOf(events));
    }

    public static EventList empty() {
        return new EventList();
    }

    public static EventList of(Event... events) {
        if (events == null) {
            return empty();
        }

        return new EventList(List.of(events));
    }

    public static EventList of(List<Event> events) {
        return new EventList(events);
    }

    public EventList add(Event... events) {
        add(List.of(events));
        return new EventList(this.events);
    }

    private EventList add(List<Event> events) {
        this.events.addAll(List.copyOf(events));
        return new EventList(this.events);
    }

    public List<Event> events() {
        return events;
    }
}
