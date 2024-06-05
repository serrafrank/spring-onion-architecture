package org.pay_my_buddy.application.common.api;

public non-sealed class AbstractCommand<R> extends AbstractRequest implements Command<R> {

    protected AbstractCommand() {
        super();
    }

}
