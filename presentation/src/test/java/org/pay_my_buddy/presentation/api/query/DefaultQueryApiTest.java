package org.pay_my_buddy.presentation.api.query;

import com.tngtech.jgiven.integration.spring.junit5.SimpleSpringScenarioTest;
import org.junit.jupiter.api.Test;


class DefaultQueryApiTest extends SimpleSpringScenarioTest<QueryApiStage> {

    @Test
    void execute_a_supported_query_succeed() {

        given().a_query()
                .and().a_query_handler_for_the_query();

        when().execute_the_query();

        then().the_query_is_executed();
    }

    @Test
    void load_two_query_handlers_with_same_query_throws_an_exception() {

        given().a_query();
        when().two_querys_handler_for_the_same_query();
        then().an_exception_is_thrown();
    }
}