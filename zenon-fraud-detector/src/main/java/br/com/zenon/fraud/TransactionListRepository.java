package br.com.zenon.fraud;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class TransactionListRepository implements TransactionRepositoryInterface {

    private final List<Transaction> transactions;

    public TransactionListRepository(List<Transaction> transactions) {
        Objects.requireNonNull(transactions);
        this.transactions = transactions;
    }

    public Optional<Transaction> findByOriginName(String name) {
        return transactions
                .stream()
                .filter(t -> t.origin().name().equals(name))
                .findFirst();
    }

    @Override
    public void save(Transaction transaction) {
        this.transactions.add(transaction);
    }
}
