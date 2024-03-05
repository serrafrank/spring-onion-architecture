package com.example.paymybuddy.application.account;

import com.example.paymybuddy.application.account.spi.AccountRepository;
import com.example.paymybuddy.application.account.use_case.create_account.CreateAccountUseCase;
import lombok.extern.java.Log;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@Log
@AutoConfiguration
public class AccountModule {


    @Bean
    public CreateAccountUseCase createAccountUseCase(AccountRepository accountRepository) {
        return new CreateAccountUseCase(accountRepository);
    }

}
