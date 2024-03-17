package org.pay_my_buddy.entity.transaction;

import com.tngtech.jgiven.junit5.SimpleScenarioTest;
import org.junit.jupiter.api.Test;
import org.pay_my_buddy.entity.account.CurrencyCode;

class TransactionTest extends SimpleScenarioTest<TransactionTestStages> {

    @Test
    void a_transaction_is_created() {
        given().a_debtor()
                .and().a_creditor()
                .and().an_amount_of_$_$(10, CurrencyCode.EUR);
        when().a_transaction();
        then().the_transaction_is_created();
    }

    @Test
    void a_transaction_is_created_without_debtor() {
        given().a_creditor()
                .and().an_amount_of_$_$(10, CurrencyCode.EUR);
        when().a_transaction();
        then().an_exception_$_is_thrown(IllegalArgumentException.class);
    }

    @Test
    void a_transaction_is_created_without_creditor() {
        given().a_debtor()
                .and().an_amount_of_$_$(10, CurrencyCode.EUR);
        when().a_transaction();
        then().an_exception_$_is_thrown(IllegalArgumentException.class);
    }

    @Test
    void a_transaction_is_created_without_amount() {
        given().a_debtor()
                .and().a_creditor();
        when().a_transaction();
        then().an_exception_$_is_thrown(IllegalArgumentException.class);
    }
}