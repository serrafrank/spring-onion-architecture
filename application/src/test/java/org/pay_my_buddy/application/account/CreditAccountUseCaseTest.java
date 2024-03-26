package org.pay_my_buddy.application.account;

import com.tngtech.jgiven.integration.spring.junit5.SimpleSpringScenarioTest;
import com.tngtech.jgiven.junit5.JGivenExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith({JGivenExtension.class, SpringExtension.class})
public class CreditAccountUseCaseTest extends SimpleSpringScenarioTest<MovementAccountStage> {

    @Test
    void credit_account_when_account_exists() {
        given().an_account_with_balance_of_$_EUR(100)
                .and().a_command_to_credit_account_of_$_EUR(50);

        when().the_account_is_credited();

        then().the_account_is_saved_with_balance_of_$_EUR(150);
    }

    @Test
    void credit_account_when_account_does_not_exist() {
        given().no_account()
                .and().a_command_to_credit_account_of_$_EUR(50);

        when().the_account_is_credited();

        then().an_exception_is_thrown(AccountNotExistsException.class);
    }
}
