package org.pay_my_buddy.application.user;

import com.tngtech.jgiven.junit5.JGivenExtension;
import com.tngtech.jgiven.junit5.SimpleScenarioTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(JGivenExtension.class)
class CreateUserUseCaseTest extends SimpleScenarioTest<CreateUserUseCaseTestStage> {

    @Test
    void create_user() {
        given().a_new_user()
                .and().the_email_is_not_used();
        when().the_user_try_to_create_an_account();
        then().the_user_is_created();
    }

    @Test
    void create_user_with_existing_email() {
        given().a_new_user()
                .and().the_email_is_already_used();
        when().the_user_try_to_create_an_account();
        then().the_user_creation_fails()
                .and().an_email_already_exists_exception_is_thrown();
    }


}