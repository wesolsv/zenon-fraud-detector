package br.com.zenon.fraud;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TransactionMapRepository implements TransactionRepositoryInterface {

    private final Map<String, Transaction> transactionByOriginNamae;

    public TransactionMapRepository(List<Transaction> transactions) {
        Objects.requireNonNull(transactions);
        this.transactionByOriginNamae =
        transactions.stream()
                .collect(Collectors.toMap(tr -> tr.origin().name(), Function.identity(), (existente, novo) -> existente));
    }

    public Optional<Transaction> findByOriginName(String name) {
        return Optional.ofNullable(transactionByOriginNamae.get(name));
    }

    @Override
    public void save(Transaction transaction) {
        this.transactionByOriginNamae.putIfAbsent(transaction.origin().name(), transaction);
    }
}
