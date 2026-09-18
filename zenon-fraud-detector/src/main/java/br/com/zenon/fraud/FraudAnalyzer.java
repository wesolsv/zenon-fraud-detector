package br.com.zenon.fraud;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FraudAnalyzer {

    public static void countFrauds(List<Transaction> transactions) {
        long count = getTransactionStream(transactions).count();
        IO.println("\n1. Contagem de Fraudes: " + count);
    }

    public static List<Transaction> biggerFrauds(List<Transaction> transactions, int qtdFrauds){
        return getTransactionStream(transactions)
                .sorted(Comparator.comparing(Transaction::amount).reversed())
                .limit(qtdFrauds)
                .toList();
    }

    public static void getClientsFrauds(List<Transaction> transactions) {

        List<Transaction> lista = biggerFrauds(transactions, 5);

        Set<Transaction> biggerSuspects = lista.stream()
                        .limit(5)
                        .collect(Collectors.toSet());

        biggerSuspects.forEach(l -> IO.println("Origin suspects: " + l.origin().name()));
    }

    public static void calcularTotal(List<Transaction> transactions){
        BigDecimal total = getTransactionStream(transactions)
                .map(Transaction::amount)
               .reduce(BigDecimal.ZERO, BigDecimal::add);

       IO.println("Valor: " + total);
    }

    public static void countWithType(List<Transaction> transactions){
        long countCashOut = getTransactionStream(transactions)
                .filter(l -> l.type().equals(TypeEnum.CASH_OUT))
                .count();

        long countTranfer = getTransactionStream(transactions)
                .filter(l -> l.type().equals(TypeEnum.TRANSFER))
                .count();
        IO.println("-CASH_OUT: " + countCashOut);
        IO.println("-TRANSFER: " + countTranfer);
    }

    private static Stream<Transaction> getTransactionStream(List<Transaction> transactions) {
        return transactions.stream()
                .filter(Transaction::isFraud);
    }
}
