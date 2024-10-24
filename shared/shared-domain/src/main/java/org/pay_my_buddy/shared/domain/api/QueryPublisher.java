package org.pay_my_buddy.shared.domain.api;

@FunctionalInterface
public interface QueryPublisher {
    <RESPONSE, QUERY extends Query<RESPONSE>> void publish(QUERY command);
}
