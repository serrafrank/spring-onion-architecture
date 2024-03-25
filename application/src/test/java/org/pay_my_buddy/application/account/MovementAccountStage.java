package org.pay_my_buddy.application.account;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.BeforeScenario;
import com.tngtech.jgiven.integration.spring.JGivenStage;
import org.junit.jupiter.api.Assertions;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.pay_my_buddy.entity.account.Account;
import org.pay_my_buddy.entity.account.api.CreditAccountCommand;
import org.pay_my_buddy.entity.account.api.DebitAccountCommand;
import org.pay_my_buddy.entity.account.spi.AccountSpi;
import org.pay_my_buddy.entity.commun.api.command.Command;
import org.pay_my_buddy.entity.commun.entity.GenericId;
import org.pay_my_buddy.entity.commun.value_object.Amount;
import org.pay_my_buddy.entity.commun.value_object.CurrencyCode;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@JGivenStage
public class MovementAccountStage extends Stage<MovementAccountStage> {

    private final AccountSpi accountSpi = Mockito.mock(AccountSpi.class);
    private final DebitAccountUseCase debitAccountUseCase = new DebitAccountUseCase(accountSpi);
    private final CreditAccountUseCase creditAccountUseCase = new CreditAccountUseCase(accountSpi);
    private final ArgumentCaptor<Account> accountCaptor = ArgumentCaptor.forClass(Account.class);

    private Account account;
    private GenericId userId = GenericId.of();
    private Amount initialBalance = Amount.of(BigDecimal.ZERO, CurrencyCode.EUR);
    private Command command;
    private Exception exception;

    @BeforeScenario
    public void setUp() {
        Mockito.reset(accountSpi);

        this.account = null;
        this.command = null;
        this.exception = null;
    }

    public MovementAccountStage an_account_with_balance_of_$_EUR(Integer amount) {
        final BigDecimal balance = BigDecimal.valueOf(amount);
        initialBalance = Amount.of(balance, CurrencyCode.EUR);
        this.account = Account.builder()
                .userId(this.userId)
                .balance(initialBalance)
                .build();
        Mockito.when(accountSpi.findByUserId(this.userId)).thenReturn(Optional.of(this.account));
        return self();
    }

    public MovementAccountStage no_account() {
        Mockito.when(accountSpi.findByUserId(this.userId)).thenReturn(Optional.empty());
        return self();
    }

    public MovementAccountStage a_command_to_debit_account_of_$_EUR(Integer amount) {
        final BigDecimal value = BigDecimal.valueOf(amount);
        final CurrencyCode currency = CurrencyCode.EUR;
        this.command = new DebitAccountCommand(this.userId, Amount.of(value, currency));
        return self();
    }

    public MovementAccountStage a_command_to_credit_account_of_$_EUR(Integer amount) {
        final BigDecimal value = BigDecimal.valueOf(amount);
        final CurrencyCode currency = CurrencyCode.EUR;
        this.command = new CreditAccountCommand(this.userId, Amount.of(value, currency));
        return self();
    }


    public MovementAccountStage the_account_is_debited() {
        Assertions.assertNotNull(this.command);

        try {
            debitAccountUseCase.handle((DebitAccountCommand) this.command);
        } catch (Exception e) {
            this.exception = e;
        }

        return self();
    }

    public MovementAccountStage the_account_is_credited() {
        Assertions.assertNotNull(this.command);

        try {
            creditAccountUseCase.handle((CreditAccountCommand) this.command);
        } catch (Exception e) {
            this.exception = e;
        }

        return self();
    }

    public MovementAccountStage the_account_is_saved_with_balance_of_$_EUR(Integer amount) {
        Assertions.assertNull(this.exception);

        Mockito.verify(accountSpi).save(accountCaptor.capture());

        this.account = accountCaptor.getValue();
        Assertions.assertNotNull(this.account);
        Assertions.assertEquals(this.userId, this.account.getUserId());
        Assertions.assertEquals(BigDecimal.valueOf(amount), this.account.getBalance().getValue());
        Mockito.verify(accountSpi).save(eq(this.account));
        return self();
    }

    public MovementAccountStage an_exception_is_thrown(Class<? extends Exception> exceptionClass) {
        Assertions.assertNotNull(this.exception);
        Assertions.assertEquals(exceptionClass, this.exception.getClass());
        return self();
    }

    public MovementAccountStage the_account_is_not_saved() {
        Mockito.verify(accountSpi, Mockito.never()).save(any());
        return self();
    }


}
