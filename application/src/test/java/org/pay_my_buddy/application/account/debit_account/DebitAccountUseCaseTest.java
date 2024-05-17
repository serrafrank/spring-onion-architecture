package org.pay_my_buddy.application.account.debit_account;

import com.tngtech.jgiven.integration.spring.EnableJGiven;
import com.tngtech.jgiven.integration.spring.junit5.SimpleSpringScenarioTest;
import com.tngtech.jgiven.junit5.JGivenExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.pay_my_buddy.application.account.AccountNotExistsException;
import org.pay_my_buddy.application.account.MovementAccountStage;
import org.pay_my_buddy.entity.account.exception.InsufficientFundsException;

@EnableJGiven
@ExtendWith(JGivenExtension.class)
class DebitAccountUseCaseTest extends SimpleSpringScenarioTest<MovementAccountStage> {

    @Test
    void debit_account_when_account_exists() {
        given().an_account_with_balance_of_$_EUR(100)
                .and().a_command_to_debit_account_of_$_EUR(50);

        when().the_account_is_debited();

        then().the_account_is_saved_with_balance_of_$_EUR(50);
    }

    @Test
    void debit_account_when_account_has_insufficient_balance() {
        given().an_account_with_balance_of_$_EUR(10)
                .and().a_command_to_debit_account_of_$_EUR(50);

        when().the_account_is_debited();

        then().an_exception_is_thrown(InsufficientFundsException.class);
    }

    @Test
    void debit_account_when_account_does_not_exist() {
        given().no_account()
                .and().a_command_to_debit_account_of_$_EUR(50);

        when().the_account_is_debited();

        then().an_exception_is_thrown(AccountNotExistsException.class);
    }
}
