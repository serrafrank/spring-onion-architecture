package org.pay_my_buddy.shared.domain.entity;

import org.pay_my_buddy.shared.domain.api.DomainEvent;
import org.pay_my_buddy.shared.domain.validator.Constraint;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDomainEntity implements DomainEntity {
    private final List<DomainEvent> domainEvents = new ArrayList<>();


    @Override
    public void addDomainEvent(DomainEvent domainEvent) {
        Constraint.checkIf(domainEvent).notNull("Domain event must not be null");
        domainEvents.add(domainEvent);
    }

    @Override
    public List<DomainEvent> domainEvents() {
        return domainEvents;
    }

    @Override
    public void clearDomainEvents() {
        domainEvents.clear();
    }
}
