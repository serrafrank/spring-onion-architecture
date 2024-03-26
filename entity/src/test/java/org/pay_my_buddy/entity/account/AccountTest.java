package org.pay_my_buddy.entity.account;

import com.tngtech.jgiven.integration.spring.junit5.SimpleSpringScenarioTest;
import org.junit.jupiter.api.Test;
import org.pay_my_buddy.entity.account.exception.CurrencyMismatchException;
import org.pay_my_buddy.entity.account.exception.InsufficientFundsException;
import org.pay_my_buddy.entity.commun.value_object.CurrencyCode;

class AccountTest extends SimpleSpringScenarioTest<AccountTestStages> {


    @Test
    void debit_account() {
        given().an_account_with_a_balance_of_$_$(10, CurrencyCode.USD);
        when().the_account_is_debited_of_$_$(10, CurrencyCode.USD);
        then().no_exception_is_thrown()
                .and().the_account_has_a_balance_of_$_$(0, CurrencyCode.USD);

    }

    @Test
    void credit_account() {
        given().an_account_with_a_balance_of_$_$(10, CurrencyCode.USD);
        when().the_account_is_credited_of_$_$(10, CurrencyCode.USD);
        then().no_exception_is_thrown()
                .and().the_account_has_a_balance_of_$_$(20, CurrencyCode.USD);
    }

    @Test
    void debit_account_with_insufficient_balance() {
        given().an_account_with_a_balance_of_$_$(10, CurrencyCode.USD);
        when().the_account_is_debited_of_$_$(20, CurrencyCode.USD);
        then().an_exception_is_thrown(InsufficientFundsException.class)
                .and().the_account_has_a_balance_of_$_$(10, CurrencyCode.USD);
    }

    @Test
    void credit_account_debited_with_different_currency() {
        given().an_account_with_a_balance_of_$_$(10, CurrencyCode.GBP);
        when().the_account_is_debited_of_$_$(20, CurrencyCode.USD);
        then().an_exception_is_thrown(CurrencyMismatchException.class)
                .and().the_account_has_a_balance_of_$_$(10, CurrencyCode.GBP);
    }

    @Test
    void credit_account_credited_with_different_currency() {
        given().an_account_with_a_balance_of_$_$(10, CurrencyCode.GBP);
        when().the_account_is_credited_of_$_$(20, CurrencyCode.USD);
        then().an_exception_is_thrown(CurrencyMismatchException.class)
                .and().the_account_has_a_balance_of_$_$(10, CurrencyCode.GBP);
    }
}