package br.com.zenon.fraud;

import br.com.zenon.util.TransactionReport;
import br.com.zenon.util.TransactionReport.Statistics;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;
import java.util.ResourceBundle;

public class ReportMain {
    static void main(String [] args) {

        String language = (args.length > 0 ? args[0] : "pt");
        var locale = Locale.of(language);

        var integerFormatter = NumberFormat.getIntegerInstance(locale);
        var currencyFormatter = DecimalFormat.getCurrencyInstance(locale);
        currencyFormatter.setCurrency(Currency.getInstance("USD"));

        var resourceBundle = ResourceBundle.getBundle("report", locale);

        var tranctionReport = new TransactionReport();
        Statistics statistics = tranctionReport.generateReport("data/dados.csv");

        String fmtTotalTransactions = integerFormatter.format(statistics.totalTransactions());
        String fmtTotalFrauds = integerFormatter.format(statistics.totalTransactions());
        String fmtTotalAmount = currencyFormatter.format(statistics.totalAmount());

        String msgTotalTransactions = resourceBundle.getString("label.total.transactions");
        String msgTotalFrauds = resourceBundle.getString("label.total.frauds");
        String msgTotalAmount = resourceBundle.getString("label.total.amount");

        IO.print("""
                %s: %s
                %s: %s
                %s: %s
                """.formatted(
                msgTotalTransactions, fmtTotalTransactions,
                msgTotalFrauds, fmtTotalFrauds,
                msgTotalAmount, fmtTotalAmount));

    }
}
