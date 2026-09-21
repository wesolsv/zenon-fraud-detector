package br.com.zenon;

import br.com.zenon.fraud.Customer;
import br.com.zenon.fraud.FraudAnalyzer;
import br.com.zenon.fraud.Transaction;
import br.com.zenon.fraud.TypeEnum;
import br.com.zenon.util.TransactionIngestor;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static br.com.zenon.fraud.TypeEnum.*;

public class Main {
    static void main() {
        var transaction1 = new Transaction(
                1,
                PAYMENT,
                new BigDecimal("9839.64"),
                new Customer("C1231006815", new BigDecimal("170136.0"),new BigDecimal("160296.36")),
                new Customer("M1979787155", new BigDecimal("0.0"), new BigDecimal("0.0")),
                false,
                false);

        var transaction2 = new Transaction(
                743,
                CASH_OUT,
                new BigDecimal("850002.52"),
                new Customer("C1280323807", new BigDecimal("850002.52"),new BigDecimal("0.0")),
                new Customer("C873221189", new BigDecimal("6510099.11"), new BigDecimal("7360101.63")),
                true,
                false);

        List<Transaction> transactions = TransactionIngestor.read("data/dados.csv");
        transactions.forEach(IO::println);

        FraudAnalyzer fraudAnalyzer = new FraudAnalyzer(transactions);

        IO.println("==============================================================================");

        //Filtrar apenas transações que são fraud == true
        IO.println("\n1. Contagem total de fraudes: " + fraudAnalyzer.countFrauds());

        //Imprimir 3 maiores fraudes com o origin name
        IO.println("\n2. Lista Top(3) Fraudes: \n");
        List<BigDecimal> findTopFraudsAmounts = fraudAnalyzer.findBiggerFrauds(3);
        findTopFraudsAmounts.forEach(amount -> IO.println("- %.2f".formatted(amount)));

        //Obter os nomes dos clientes de origem das fraudes sem repetições
        IO.println("\n3. Lista Top(5) Maiores Suspeitos: \n");
        Set<Transaction> clientsSuspicious = fraudAnalyzer.getClientsFrauds(5);
        clientsSuspicious.forEach(l -> IO.println("Origin suspects: " + l.origin().name()));

        //Calcular o valor do prejuizo total
        BigDecimal totalFraudLoss = fraudAnalyzer.calculateTotalFraudLoss();
        IO.println("\n4. Prejuizo Total: " + totalFraudLoss);

        IO.println("\n5. Fraudes por Tipo:\n");
        Map<TypeEnum, Long> fraudsCountByType = fraudAnalyzer.countByType();
        fraudsCountByType.forEach((type, count) -> IO.println("-%s: %d".formatted(type, count)));

//        List<Transaction> transactionsBadData = TransactionIngestor.read("data/paysim_with_bad_data.csv");
//        transactionsBadData.forEach(IO::println);
    }
}
