package br.com.zenon.fraud;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface TransactionListRepositoryInterface {

    Optional<Transaction> findByOriginName(String name);
}
