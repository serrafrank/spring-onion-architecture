package org.pay_my_buddy.application.transaction;

import lombok.RequiredArgsConstructor;
import org.pay_my_buddy.entity.commun.ApplicationService;
import org.pay_my_buddy.entity.commun.api.query.QueryHandler;
import org.pay_my_buddy.entity.transaction.Transaction;
import org.pay_my_buddy.entity.transaction.TransactionId;
import org.pay_my_buddy.entity.transaction.api.FindTransactionByIdQuery;
import org.pay_my_buddy.entity.transaction.spi.TransactionSpi;

import java.util.Optional;

@ApplicationService
@RequiredArgsConstructor
public class FindTransactionByIdUseCase implements QueryHandler<FindTransactionByIdQuery, Optional<Transaction>> {

    private final TransactionSpi transactionSpi;

    @Override
    public Optional<Transaction> handle(FindTransactionByIdQuery findTransactionByIdQuery) {
        final TransactionId id = findTransactionByIdQuery.id();
        return transactionSpi.findById(id);
    }

}
