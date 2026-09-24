package br.com.zenon.fraud;

import java.util.Optional;

public interface TransactionRepositoryInterface {

    Optional<Transaction> findByOriginName(String name);

    void save(Transaction transaction);
}
