package br.com.zenon.fraud;

import java.math.BigDecimal;

public record Transaction(
        int step,
        TypeEnum type,
        BigDecimal amount,
        Customer customer,
        Customer destination,
        boolean isFraud,
        boolean isFlaggedFraud
        )
{}
