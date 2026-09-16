package br.com.zenon;

import br.com.zenon.fraud.Customer;
import br.com.zenon.fraud.Transaction;
import br.com.zenon.util.TransactionIngestor;

import java.math.BigDecimal;
import java.util.List;

import static br.com.zenon.fraud.TypeEnum.*;

public class Main {
    static void main() throws Exception {
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

        List<Transaction> transactions = TransactionIngestor.readFileNIO2("data/dados.csv");
        transactions.forEach(System.out::println);
    }
}
