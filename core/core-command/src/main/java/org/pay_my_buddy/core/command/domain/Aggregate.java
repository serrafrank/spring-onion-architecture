package org.pay_my_buddy.core.command.domain;

public interface Aggregate<AGGREGATE> {
    void on(AbstractAggregateRoot.CreateSnapshotAggregateEvent<AGGREGATE> event);
}
