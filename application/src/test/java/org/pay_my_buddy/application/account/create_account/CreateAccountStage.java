package org.pay_my_buddy.application.account.create_account;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.BeforeScenario;
import com.tngtech.jgiven.integration.spring.JGivenStage;
import org.junit.jupiter.api.Assertions;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.pay_my_buddy.application.account.CreateAccountUseCase;
import org.pay_my_buddy.entity.account.Account;
import org.pay_my_buddy.entity.account.api.CreateAccountCommand;
import org.pay_my_buddy.entity.account.spi.AccountSpi;
import org.pay_my_buddy.entity.common.entity.GenericId;
import org.pay_my_buddy.entity.common.entity.Id;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@JGivenStage
public class CreateAccountStage extends Stage<CreateAccountStage> {

    private final AccountSpi accountSpi = Mockito.mock(AccountSpi.class);
    private final CreateAccountUseCase createAccountUseCase = new CreateAccountUseCase(accountSpi);
    private final ArgumentCaptor<Account> accountCaptor = ArgumentCaptor.forClass(Account.class);

    private Id userId;
    private Account account;
    private CreateAccountCommand command;
    private Exception exception;

    @BeforeScenario
    public void setUp() {
        Mockito.reset(accountSpi);

        this.userId = GenericId.of();
        this.account = null;
        this.command = null;
        this.exception = null;
    }


    public CreateAccountStage an_account_exists_for_this_user() {
        Mockito.when(accountSpi.existsForUserId(any())).thenReturn(true);
        return self();
    }

    public CreateAccountStage an_account_dont_exist_for_this_user() {
        Mockito.when(accountSpi.existsForUserId(any())).thenReturn(false);
        return self();
    }

    public CreateAccountStage a_command_to_create_account() {
        this.command = new CreateAccountCommand(userId);
        return self();
    }

    public CreateAccountStage the_command_is_fired() {
        Assertions.assertNotNull(this.command);

        try {
            createAccountUseCase.handle(this.command);
        } catch (Exception e) {
            this.exception = e;
        }

        return self();
    }

    public CreateAccountStage the_account_is_saved() {
        Assertions.assertNull(this.exception);

        Mockito.verify(accountSpi).save(accountCaptor.capture());

        this.account = accountCaptor.getValue();
        Assertions.assertNotNull(this.account);
        Assertions.assertEquals(this.userId, this.account.getUserId());
        Assertions.assertEquals(BigDecimal.ZERO, this.account.getBalance().value());

        Mockito.verify(accountSpi).save(eq(this.account));
        return self();
    }

    public CreateAccountStage an_exception_is_thrown(Class<? extends Exception> exceptionClass) {
        Assertions.assertNotNull(this.exception);
        Assertions.assertTrue(exceptionClass.isInstance(this.exception));
        return self();
    }

    public CreateAccountStage the_account_is_not_saved() {
        Mockito.verify(accountSpi, Mockito.never()).save(any());
        return self();
    }


}
