package org.pay_my_buddy.entity.common.api.query;

import org.pay_my_buddy.entity.common.api.Request;

/**
 * This is the Query interface.
 * It serves as a marker interface for all query classes in the application.
 * A query represents an operation or action that the application can perform.
 * Each specific query will implement this interface.
 */
public interface Query<R> extends Request {
}
