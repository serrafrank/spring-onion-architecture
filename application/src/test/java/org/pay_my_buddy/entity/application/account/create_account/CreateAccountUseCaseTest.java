package org.pay_my_buddy.entity.application.account.create_account;

import com.tngtech.jgiven.integration.spring.EnableJGiven;
import com.tngtech.jgiven.integration.spring.junit5.SimpleSpringScenarioTest;
import com.tngtech.jgiven.junit5.JGivenExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.pay_my_buddy.entity.application.account.exception.AccountAlreadyExistsException;

@EnableJGiven
@ExtendWith(JGivenExtension.class)
class CreateAccountUseCaseTest extends SimpleSpringScenarioTest<CreateAccountStage> {

    @Test
    @DisplayName("Create an account when another account exists for a user throw exception")
    void create_account_when_another_account_exists_for_a_user_throw_exception() {
        given().an_account_exists_for_this_user()
                .and().a_command_to_create_account();

        when().the_command_is_fired();

        then().an_exception_is_thrown(AccountAlreadyExistsException.class)
                .and().the_account_is_not_saved();
    }

    @Test
    @DisplayName("Create an account when no account exists")
    void create_account_when_no_account_exists_for_a_user() {
        given().an_account_dont_exist_for_this_user()
                .and().a_command_to_create_account();

        when().the_command_is_fired();

        then().the_account_is_saved();
    }
}
