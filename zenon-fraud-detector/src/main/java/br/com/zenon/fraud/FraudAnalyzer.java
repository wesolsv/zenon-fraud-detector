package br.com.zenon.fraud;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FraudAnalyzer {

    private final List<Transaction> transactions;

    public FraudAnalyzer(List<Transaction> transactions) {
        Objects.requireNonNull(transactions);
        this.transactions = transactions;
    }

    public long countFrauds() {
        return generateFraudStream().count();
    }

    public List<BigDecimal> findBiggerFrauds(int qtdFrauds){
        return highValueFraudStream()
                .map(Transaction::amount)
                .limit(qtdFrauds)
                .toList();
    }

    public Set<Transaction> getClientsFrauds(int qtdFrauds) {

        return highValueFraudStream()
                .limit(qtdFrauds)
                .collect(Collectors.toSet());
    }

    public BigDecimal calculateTotalFraudLoss(){
        return generateFraudStream()
                .map(Transaction::amount)
               .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Map<TypeEnum, Long> countByType(){
        return generateFraudStream()
                .collect(Collectors.groupingBy(Transaction::type, Collectors.counting()));
    }
    
    private Stream<Transaction> generateFraudStream() {
        return transactions.stream()
                .filter(Transaction::isFraud);
    }

    private Stream<Transaction> highValueFraudStream() {
        return generateFraudStream()
                .sorted(Comparator.comparing(Transaction::amount).reversed());
    }
}
