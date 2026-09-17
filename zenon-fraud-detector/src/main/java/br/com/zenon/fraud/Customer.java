package br.com.zenon.fraud;

import java.math.BigDecimal;
import java.util.Objects;

public record Customer(
        String name,
        BigDecimal oldBalance,
        BigDecimal newBalance )
{

    public Customer {
        Objects.requireNonNull(name, "Customer name required");
        Objects.requireNonNull(oldBalance, "Customer oldBalance required");
        Objects.requireNonNull(newBalance, "Customer newBalance required");

        if(oldBalance.signum() < 0) throw new IllegalArgumentException("Old Balance deve ser positivo ou 0");
        if(newBalance.signum() < 0) throw new IllegalArgumentException("Old Balance deve ser positivo ou 0");
        if(name.isBlank()) throw new IllegalArgumentException("Name de Customer deve ser informado");
    }
}
