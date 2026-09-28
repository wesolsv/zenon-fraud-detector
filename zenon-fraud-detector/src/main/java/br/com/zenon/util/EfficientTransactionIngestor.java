package br.com.zenon.util;

import br.com.zenon.fraud.Transaction;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class EfficientTransactionIngestor {

    public static final int LINE_BATCH_SIZE = 2_500;

    public void readAsBatch(String nomeArquivo, Consumer<List<Transaction>> batchConsumer) {
        AtomicInteger contador = new AtomicInteger();
        try (ExecutorService executor = Executors.newFixedThreadPool(10);
             Stream<String> lines = Files.lines(Paths.get(nomeArquivo)).skip(1)) {
            var iterator = lines.iterator();

            List<String> lineBatch = new ArrayList<>(LINE_BATCH_SIZE);
            while (iterator.hasNext()) {
                String line = iterator.next();
                lineBatch.add(line);

                if (lineBatch.size() >= LINE_BATCH_SIZE) {
                    IO.println("Executando Batch INGESTOR");
                    final List<String> currentLineBatch = List.copyOf(lineBatch);
                    executor.submit(() -> executeBatch(currentLineBatch, batchConsumer));
                    lineBatch.clear();
                }
            }
            if (!lineBatch.isEmpty()) {
                IO.println("Batch final INGESTOR");
                final List<String> currentLineBatch = List.copyOf(lineBatch);
                executor.submit(() -> executeBatch(currentLineBatch, batchConsumer));
            }
        } catch (Exception ex) {
            System.err.println("Erro de dados: " + ex.getMessage());
        }
        IO.println(contador.toString());
    }

    private void executeBatch(List<String> lineBatch, Consumer<List<Transaction>> batchConsumer) {
        List<Transaction> transactionBatch = lineBatch.stream().map(Transaction::parseTransacaoBatch)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
        batchConsumer.accept(transactionBatch);
    }
}
