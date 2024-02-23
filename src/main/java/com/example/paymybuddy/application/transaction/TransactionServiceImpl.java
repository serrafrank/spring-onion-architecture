package com.example.paymybuddy.application.transaction;

import com.example.paymybuddy.application.user.command.CreditUserCommand;
import com.example.paymybuddy.application.user.command.DebitUserCommand;
import com.example.paymybuddy.application.user.command.UserCommand;
import com.example.paymybuddy.application.user.query.AreFriendsQuery;
import com.example.paymybuddy.application.user.query.UserQuery;
import com.example.paymybuddy.core.common.entity.id.Id;
import com.example.paymybuddy.core.common.valueobject.Amount;
import com.example.paymybuddy.core.transaction.TransactionAggregate;
import com.example.paymybuddy.core.transaction.valueobject.TransactionId;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserCommand userCommand;
    private final UserQuery userQuery;

    @Override
    public Optional<TransactionAggregate> findTransaction(TransactionId id) {
        return transactionRepository.findTransaction(id);
    }

    @Override
    public TransactionId createTransaction(Id debtorId, Id creditorId, Amount amount) {

        if (debtorId.equals(creditorId)) {
            throw new IllegalArgumentException("Debtor and creditor cannot be the same");
        }

        final boolean areFriends = userQuery.areFriends(new AreFriendsQuery(debtorId, creditorId));
        if (!areFriends) {
            throw new IllegalArgumentException("Debtor and creditor are not friends");
        }

        userCommand.debitUser(new DebitUserCommand(debtorId, amount));
        userCommand.creditUser(new CreditUserCommand(creditorId, amount));

        TransactionAggregate transaction = new TransactionAggregate(debtorId, creditorId, amount);

        return transactionRepository.saveTransaction(transaction);
    }

}
