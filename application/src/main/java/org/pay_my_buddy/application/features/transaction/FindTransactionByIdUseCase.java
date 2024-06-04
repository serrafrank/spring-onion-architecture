package org.pay_my_buddy.application.features.transaction;

import lombok.RequiredArgsConstructor;
import org.pay_my_buddy.application.common.DomainService;
import org.pay_my_buddy.application.common.api.query.QueryHandler;
import org.pay_my_buddy.application.features.transaction.spi.TransactionSpi;
import org.pay_my_buddy.entity.transaction.Transaction;
import org.pay_my_buddy.entity.transaction.TransactionId;

import java.util.Optional;

@DomainService
@RequiredArgsConstructor
public class FindTransactionByIdUseCase implements QueryHandler<FindTransactionByIdQuery, Optional<Transaction>> {

    private final TransactionSpi transactionSpi;

    @Override
    public Optional<Transaction> handle(FindTransactionByIdQuery findTransactionByIdQuery) {
        final TransactionId id = findTransactionByIdQuery.id();
        return transactionSpi.findById(id);
    }

}
