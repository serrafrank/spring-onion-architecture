package org.pay_my_buddy.core.framework.application;

import org.pay_my_buddy.core.framework.domain.message.Query;

public interface QueryBus {

    <QUERY extends Query<RESPONSE>, RESPONSE> RESPONSE ask(QUERY query);
}
