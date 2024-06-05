package org.pay_my_buddy.application.common.api;

public non-sealed class AbstractEvent extends AbstractRequest implements Event {

    /**
     * This constructor is used to create a new event object from an existing event object.
      */
    protected AbstractEvent(Event eventObject) {
        super(eventObject.metadata());
    }

    /**
     * This constructor is used to create a new event object from a request object.
     */
    protected AbstractEvent(Request request) {
        super(request);
    }

}
