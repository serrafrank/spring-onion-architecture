package org.pay_my_buddy.entity.transaction;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.integration.spring.JGivenStage;
import org.pay_my_buddy.entity.commun.entity.GenericId;
import org.pay_my_buddy.entity.commun.entity.Id;
import org.pay_my_buddy.entity.commun.value_object.Amount;
import org.pay_my_buddy.entity.commun.value_object.CurrencyCode;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@JGivenStage
class TransactionTestStages extends Stage<TransactionTestStages> {

    private Id debtorId;
    private Id creditorId;
    private Amount amount;
    private Transaction transaction;
    private Exception exception;

    public TransactionTestStages a_debtor() {
        this.debtorId = GenericId.of();
        return self();
    }

    public TransactionTestStages a_creditor() {
        this.creditorId = GenericId.of();
        return self();
    }

    public TransactionTestStages an_amount_of_$_$(int value, CurrencyCode currency) {
        this.amount = Amount.of(BigDecimal.valueOf(value), currency);
        return self();
    }

    public TransactionTestStages a_transaction() {
        try {
            this.transaction = new Transaction(debtorId, creditorId, amount);
        } catch (Exception e) {
            this.exception = e;
        }
        return self();
    }

    public TransactionTestStages the_transaction_is_created() {
        assertThat(this.transaction).isNotNull();
        assertThat(this.transaction.getDebtorId()).isEqualTo(debtorId);
        assertThat(this.transaction.getCreditorId()).isEqualTo(creditorId);
        assertThat(this.transaction.getAmount()).isEqualTo(amount);
        return self();
    }

    public TransactionTestStages an_exception_$_is_thrown(Class<? extends Exception> exception) {
        assertThat(this.exception).isNotNull();
        assertThat(this.exception).isInstanceOf(exception);
        return self();
    }

}