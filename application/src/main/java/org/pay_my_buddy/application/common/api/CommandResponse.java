package org.pay_my_buddy.application.common.api;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public final class CommandResponse<R> {

    private final List<Event> events = new LinkedList<>();
    private final Optional<R> response;

    private CommandResponse(Optional<R> response , List<Event> events) {
        this.response = response;
        this.events.addAll(List.copyOf(events));
    }

    public static CommandResponse<Void> empty() {
        return new CommandResponse<>(Optional.empty(), List.of());
    }

    public static <R> CommandResponse<R> of(R response) {
        return new CommandResponse<>(Optional.of(response), List.of());
    }

    public CommandResponse<R> add(Event... events) {
        add(List.of(events));
        return new CommandResponse<>(this.response, this.events);
    }

    private CommandResponse<R> add(List<Event> events) {
        this.events.addAll(List.copyOf(events));
        return new CommandResponse<>(this.response, this.events);
    }

    public Optional<R> response() {
        return response;
    }

    public boolean hasResponse() {
        return response.isPresent();
    }

    public List<Event> events() {
        return events;
    }
}
