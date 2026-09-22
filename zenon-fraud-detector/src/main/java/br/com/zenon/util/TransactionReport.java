package br.com.zenon.util;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class TransactionReport {

    private record ReportTransaction(BigDecimal amaunt, boolean isFraud){}

    public record Statistics(long totalTransactions, long totalFrauds, BigDecimal totalAmount){

        private final static Statistics ZERO = new Statistics(0, 0, BigDecimal.ZERO);

        private Statistics addReportTransaction(ReportTransaction rt){
            return new Statistics(
                    totalTransactions+1,
                    totalFrauds + (rt.isFraud ? 1 : 0),
                    totalAmount.add(rt.amaunt));
        }

        private Statistics addStatistics(Statistics other){
            return new Statistics(totalTransactions + other.totalTransactions,
                    totalFrauds + other.totalFrauds,
                    totalAmount.add(other.totalAmount));
        }
    }

    public Statistics generateReport(String nomeArquivo) {
        Path path = Paths.get(nomeArquivo);

        try (Stream<String> linhas = Files.lines(path)){
            return linhas
                    .skip(1)
                    .map(this::parseTransacao)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .reduce(Statistics.ZERO, Statistics::addReportTransaction, Statistics::addStatistics);
        } catch (Exception ex){
            throw new RuntimeException("Erro ao ler arquivo " + nomeArquivo, ex);
        }
    }

    private Optional<ReportTransaction> parseTransacao(String dados) {
        try {
            String[] linha = dados.trim().split(",");

            BigDecimal amount = new BigDecimal(linha[2]);
            boolean isFraud = "1".equals(linha[9]);

            return Optional.of(new ReportTransaction(amount, isFraud));
        } catch (Exception e) {
            System.err.println("Erro de dados: " + dados + " -> " + (e.getMessage() != null ? e.getMessage() : e));
        }
        return Optional.empty();
    }
}
