package org.pay_my_buddy.entity.account;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.integration.spring.JGivenStage;
import org.pay_my_buddy.entity.commun.value_object.Amount;
import org.pay_my_buddy.entity.commun.value_object.CurrencyCode;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@JGivenStage
class AmountTestStages extends Stage<AmountTestStages> {

    private Amount amount;
    private Exception exception;

    public AmountTestStages an_amount_of_$_$(int value, CurrencyCode currency) {
        this.amount = Amount.of(BigDecimal.valueOf(value), currency);
        return self();
    }

    public AmountTestStages the_amount_is_debited_of_$_$(int value, CurrencyCode currency) {
        final var amountToDebit = Amount.of(BigDecimal.valueOf(value), currency);
        try {
            this.amount = this.amount.subtract(amountToDebit);
        } catch (Exception e) {
            this.exception = e;
        }
        return self();
    }

    public AmountTestStages the_amount_is_credited_of_$_$(int value, CurrencyCode currency) {
        try {
            this.amount = this.amount.add(Amount.of(BigDecimal.valueOf(value), currency));
        } catch (Exception e) {
            this.exception = e;
        }
        return self();
    }

    public AmountTestStages the_amount_has_a_value_of_$_$(int value, CurrencyCode currency) {
        assertThat(this.exception).isNull();

        assertThat(this.amount.getValue())
                .isEqualTo(BigDecimal.valueOf(value));
        return self();
    }

    public AmountTestStages an_exception_$_is_thrown(Class<? extends Exception> exception) {
        assertThat(this.exception).isNotNull();
        assertThat(this.exception).isInstanceOf(exception);
        return self();
    }
}