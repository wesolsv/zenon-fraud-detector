package br.com.zenon.fraud;

import br.com.zenon.util.TransactionIngestor;

import java.math.BigDecimal;
import java.util.List;

import static br.com.zenon.fraud.TypeEnum.CASH_OUT;

public class DBMain {

    void main() {

        var repository = new TransactionSQLRepository();

        long iniSql = System.nanoTime();

        List<Transaction> transactions = TransactionIngestor.read("data/dados.csv");
        IO.println(transactions.size());

        IO.println("Iniciou save de transactions BD");

        transactions.forEach(repository::save);

        IO.println("Finalizou save de transactions BD");

        long fimSql = System.nanoTime();

        IO.println("MAP= Tempo (ms) : " + (fimSql-iniSql)/1_000_000.0);

        repository.findByOriginName("C1231006815")
                .ifPresentOrElse(IO::println, () -> IO.println("Transação não encontrada"));
    }
}
