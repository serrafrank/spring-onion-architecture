package org.pay_my_buddy.presentation.api.event;

import com.tngtech.jgiven.junit5.SimpleScenarioTest;
import org.junit.jupiter.api.Test;

class DefaultEventApiTest extends SimpleScenarioTest<EventApiStage> {

    @Test
    void execute_a_supported_event_succeed() {

        given().a_event()
                .and().a_event_handler_for_the_event();

        when().execute_the_event();

        then().the_event_is_executed();
    }

    @Test
    void load_two_event_handlers_with_same_event_succeed() {


        given().a_event()
                .and().two_events_handler_for_the_same_event();
        when().execute_the_event();

        then().the_event_is_executed();
    }
}