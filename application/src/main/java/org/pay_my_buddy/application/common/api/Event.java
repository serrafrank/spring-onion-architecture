package org.pay_my_buddy.application.common.api;

/**
 * This is the Event interface.
 * It serves as a marker interface for all event classes in the application.
 * AN event represents an operation or action that the application can perform.
 * Each specific event will implement this interface.
 */
public sealed interface Event extends Request permits AbstractEvent {

}
