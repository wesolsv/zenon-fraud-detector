package br.com.zenon.fraud;

import br.com.zenon.util.TransactionReport;
import br.com.zenon.util.TransactionReport.Statistics;

public class ReportMain {
    static void main() {
        var tranctionReport = new TransactionReport();
        Statistics statistics = tranctionReport.generateReport("data/dados.csv");

        IO.print("""
                Total de linhas: %d
                Total de fraudes: %d
                Valor total transacionado: %.2f
                """.formatted(statistics.totalTransactions(), statistics.totalFrauds(), statistics.totalAmount()));

    }
}
