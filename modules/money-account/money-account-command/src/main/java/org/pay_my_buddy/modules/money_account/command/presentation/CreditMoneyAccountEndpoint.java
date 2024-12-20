package org.pay_my_buddy.modules.money_account.command.presentation;

import lombok.RequiredArgsConstructor;
import org.pay_my_buddy.modules.money_account.shared.MoneyAccountId;
import org.pay_my_buddy.modules.money_account.shared.command.CreditMoneyAccountCommand;
import org.pay_my_buddy.modules.money_account.shared.command.MoneyAccountGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Currency;

@RestController
@RequestMapping("/api/money_account")
@RequiredArgsConstructor
public class CreditMoneyAccountEndpoint {

    private final MoneyAccountGateway moneyAccountGateway;


    @PostMapping("{id}/credit")
    public ResponseEntity<?> creditMoneyAccount(
            @PathVariable("id") String id,
            @Validated @RequestBody CreditMoneyAccountRequest request) {

        var moneyAccountId = new MoneyAccountId(id);
        var command = new CreditMoneyAccountCommand(moneyAccountId, request.amount(), request.currency());
        moneyAccountGateway.handle(command);

        return ResponseEntity.ok().build();
    }

    public record CreditMoneyAccountRequest(
            BigDecimal amount,
            Currency currency) {

        public CreditMoneyAccountRequest(Long amount, String currency){
            this(BigDecimal.valueOf(amount), Currency.getInstance(currency));
        }
    }

}
