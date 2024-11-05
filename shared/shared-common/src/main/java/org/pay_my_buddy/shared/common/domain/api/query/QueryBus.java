package org.pay_my_buddy.shared.common.domain.api.query;

public interface QueryBus {

    <QUERY extends Query<RESPONSE>, RESPONSE> RESPONSE ask(QUERY query);

}
