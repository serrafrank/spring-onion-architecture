package org.pay_my_buddy.shared.command.domain;

import lombok.extern.slf4j.Slf4j;
import org.pay_my_buddy.shared.common.domain.api.Event;
import org.pay_my_buddy.shared.common.domain.entity.EntityId;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public abstract class AggregateRoot<ID extends EntityId> {
    protected ID id;
    private int version = 0;

    private final List<Event> changes = new ArrayList<>();

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

    public void markChangesAsCommitted() {
        this.changes.clear();
    }

    public void raiseEvent(Event event) {
        applyChange(event);
        changes.add(event);
    }

    public void replayEvents(Iterable<Event> events) {
        events.forEach(this::applyChange);
    }

    protected abstract void applyChange(Event event);

}

