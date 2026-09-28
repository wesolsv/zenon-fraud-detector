package br.com.zenon.fraud;

import br.com.zenon.util.EfficientTransactionIngestor;

import java.util.List;

public class IngestionMain {

    void main() {

        var repository = new TransactionSQLRepository();

        long iniSql = System.nanoTime();

        var transactionIngestor = new EfficientTransactionIngestor();

        transactionIngestor.readAsBatch("data/dados.csv", repository::saveAll);

        long fimSql = System.nanoTime();

        IO.println("Finalizou save Tempo (ms) : " + (fimSql-iniSql)/1_000_000.0);

    }
}
