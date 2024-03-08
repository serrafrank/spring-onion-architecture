package org.pay_my_buddy.presentation.api.command;

import com.tngtech.jgiven.junit5.SimpleScenarioTest;
import org.junit.jupiter.api.Test;


class DefaultCommandApiTest extends SimpleScenarioTest<CommandApiStage> {

    @Test
    void execute_a_supported_command_succeed() {

        given().a_command()
                .and().a_command_handler_for_the_command();

        when().execute_the_command();

        then().the_command_is_executed();
    }

    @Test
    void load_two_command_handlers_with_same_command_throws_an_exception() {

        given().a_command();
        when().two_commands_handler_for_the_same_command();
        then().an_exception_is_thrown();
    }
}