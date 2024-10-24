package org.pay_my_buddy.shared.domain.api;

@FunctionalInterface
public interface QueryMessageHandler<QUERY extends Query> extends MessageHandler<QUERY> {

}