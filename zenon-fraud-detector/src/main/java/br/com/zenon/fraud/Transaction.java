package br.com.zenon.fraud;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public record Transaction(
        int step,
        TypeEnum type,
        BigDecimal amount,
        Customer origin,
        Customer recipient,
        boolean isFraud,
        boolean isFlaggedFraud)
{

    public Transaction {
        Objects.requireNonNull(type, "Type required");
        Objects.requireNonNull(amount, "Amount required");
        Objects.requireNonNull(origin, "Customer origin required");
        Objects.requireNonNull(recipient, "Customer recipient required");

        if(step <= 0) throw new IllegalArgumentException("Step deve ser positivo");
        if(!TypeEnum.validateEnum(type.name())) throw new IllegalArgumentException("Type invalido");
        if(amount.signum() < 0) throw new IllegalArgumentException("Amount deve ser positivo ou zero");
    }

    public static Optional<Transaction> parseTransacao(String dados, AtomicInteger contador) {

        String[] linha = dados.trim().split(",");

        try {
            int step = Integer.parseInt(linha[0]);
            String type = linha[1];
            BigDecimal amount = new BigDecimal(linha[2]);
            Customer origin = new Customer(linha[3], new BigDecimal(linha[4]), new BigDecimal(linha[5]));
            Customer recipient = new Customer(linha[6], new BigDecimal(linha[7]), new BigDecimal(linha[8]));
            boolean isFraud = "1".equals(linha[9]);
            boolean isFlaggedFraud = "1".equals(linha[10]);

            return Optional.of(new Transaction(step, TypeEnum.valueOf(type), amount, origin, recipient, isFraud, isFlaggedFraud));

        } catch (Exception e) {
            contador.incrementAndGet();
            System.err.println("Erro de dados: " + dados + " -> " + (e.getMessage() != null ? e.getMessage() : e));
        }
        return Optional.empty();
    }
}
