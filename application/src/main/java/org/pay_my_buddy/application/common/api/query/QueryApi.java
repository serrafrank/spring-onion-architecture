package org.pay_my_buddy.application.common.api.query;

/**
 * The QueryApi interface is the entry point for all queries in the application.
 * It provides a method to execute a query.
 */
public interface QueryApi {

    <R, Q extends Query<R>> R request(Q query);

}