package org.pay_my_buddy.core.framework.domain;


import org.pay_my_buddy.core.framework.domain.message.Message;

public interface UseCaseHandler<MESSAGE extends Message, RESPONSE> {

    RESPONSE handle(MESSAGE message);
}
