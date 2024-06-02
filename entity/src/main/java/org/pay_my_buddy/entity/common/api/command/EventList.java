package org.pay_my_buddy.entity.common.api.command;

import org.pay_my_buddy.entity.common.api.event.Event;

import java.util.LinkedList;
import java.util.List;

public final class EventList {

    private final List<Event> events = new LinkedList<>();

    private EventList() {
    }

    public static EventList empty() {
        return new EventList();
    }

    public static EventList of(Event... events) {
        EventList eventList = new EventList();
        eventList.add(events);
        return eventList;
    }

    public static EventList of(List<Event> events) {
        EventList eventList = new EventList();
        eventList.add(events);
        return eventList;
    }

    public void add(Event... events) {
        add(List.of(events));
    }

    private void add(List<Event> events) {
        this.events.addAll(events);
    }

    public List<Event> events() {
        return events;
    }
}
