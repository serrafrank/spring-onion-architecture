package org.pay_my_buddy.entity.account;

import com.tngtech.jgiven.integration.spring.junit5.SimpleSpringScenarioTest;
import org.junit.jupiter.api.Test;
import org.pay_my_buddy.entity.account.exception.CurrencyMismatchException;
import org.pay_my_buddy.entity.commun.value_object.CurrencyCode;

class AmountTest extends SimpleSpringScenarioTest<AmountTestStages> {

    @Test
    void debit_amount() {
        given().an_amount_of_$_$(10, CurrencyCode.USD);
        when().the_amount_is_debited_of_$_$(10, CurrencyCode.USD);
        then().the_amount_has_a_value_of_$_$(0, CurrencyCode.USD);
    }

    @Test
    void debit_amount_with_currency_mismatch() {
        given().an_amount_of_$_$(10, CurrencyCode.EUR);
        when().the_amount_is_debited_of_$_$(20, CurrencyCode.USD);
        then().an_exception_$_is_thrown(CurrencyMismatchException.class);
    }

    @Test
    void credit_amount() {
        given().an_amount_of_$_$(10, CurrencyCode.USD);
        when().the_amount_is_credited_of_$_$(10, CurrencyCode.USD);
        then().the_amount_has_a_value_of_$_$(20, CurrencyCode.USD);
    }

    @Test
    void credit_amount_with_currency_mismatch() {
        given().an_amount_of_$_$(10, CurrencyCode.EUR);
        when().the_amount_is_credited_of_$_$(20, CurrencyCode.USD);
        then().an_exception_$_is_thrown(CurrencyMismatchException.class);
    }


}