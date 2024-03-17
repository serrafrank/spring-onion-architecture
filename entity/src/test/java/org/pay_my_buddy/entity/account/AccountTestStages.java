package org.pay_my_buddy.entity.account;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.integration.spring.JGivenStage;
import org.assertj.core.api.Assertions;
import org.pay_my_buddy.entity.commun.entity.GenericId;
import org.pay_my_buddy.entity.commun.entity.Id;

import java.math.BigDecimal;

@JGivenStage
public class AccountTestStages extends Stage<AccountTestStages> {

    private final AccountId accountId = AccountId.of();
    private final Id userId = GenericId.of();


    private Account account = new Account(GenericId.of());
    private BigDecimal initialBalance = BigDecimal.TEN;
    private CurrencyCode currency = CurrencyCode.EUR;


    private Exception exception;

    public AccountTestStages an_account_with_a_balance_of_$_$(int balance, CurrencyCode currency) {
        this.initialBalance = BigDecimal.valueOf(balance);
        this.currency = currency;
        this.account = createAccount();
        return self();
    }

    public AccountTestStages the_account_is_debited_of_$_$(int amount, CurrencyCode currency) {
        final Amount amountToDebit = new Amount(BigDecimal.valueOf(amount), currency);
        try {
            this.account.debit(amountToDebit);
        } catch (Exception e) {
            this.exception = e;
        }
        return self();
    }

    public AccountTestStages the_account_is_credited_of_$_$(int amount, CurrencyCode currency) {
        final Amount amountToCredit = new Amount(BigDecimal.valueOf(amount), currency);
        try {
            this.account.credit(amountToCredit);
        } catch (Exception e) {
            this.exception = e;
        }
        return self();
    }

    public AccountTestStages the_account_has_a_balance_of_$_$(int balance, CurrencyCode currency) {
        Assertions.assertThat(this.account.getBalance())
                .extracting(Amount::getValue, amount -> amount.getCurrency().getCurrencyCode())
                .containsExactly(BigDecimal.valueOf(balance), currency.getCode());
        return self();
    }

    public AccountTestStages no_exception_is_thrown() {
        Assertions.assertThat(this.exception).isNull();
        return self();
    }

    public AccountTestStages an_exception_is_thrown(Class<? extends Exception> exceptionClass) {
        Assertions.assertThat(this.exception).isInstanceOf(exceptionClass);
        return self();
    }


    private Account createAccount() {
        return Account.builder()
                .id(accountId)
                .userId(userId)
                .balance(new Amount(initialBalance, currency))
                .build();
    }
}
