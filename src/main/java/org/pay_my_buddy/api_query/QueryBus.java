package org.pay_my_buddy.api_query;

public interface QueryBus {

	<QUERY extends Query<RESPONSE>, RESPONSE> RESPONSE ask(QUERY query);
}
