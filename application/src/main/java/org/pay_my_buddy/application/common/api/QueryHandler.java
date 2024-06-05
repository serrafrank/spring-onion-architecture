package org.pay_my_buddy.application.common.api;

/**
 * The QueryHandler interface extends the Handler interface with a type parameter of Query.
 * It provides a method to execute a query.
 * Each specific query handler will implement this interface.
 *
 * @param <Q> The type of the query, which extends Query.
 * @param <R> The type of the result of the query.
 */
public interface QueryHandler<Q extends Query<R>, R> extends Handler<Q> {

    R handle(Q query);
}