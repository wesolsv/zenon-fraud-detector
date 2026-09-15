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
{
        public static Transaction montaTransacao(String[] linha){
                if(linha.length != 3){}

                String id = linha[0].trim();
                TypeEnum type = TypeEnum.valueOf(linha[1].trim());
                BigDecimal amount = new BigDecimal(linha[2].trim());
                Customer customer = new Customer(linha[3].trim(),  new BigDecimal(linha[4].trim()), new BigDecimal(linha[5].trim()));
                Customer destination = new Customer(linha[6].trim(),  new BigDecimal(linha[7].trim()), new BigDecimal(linha[8].trim()));
                boolean isFraud = Boolean.parseBoolean(linha[9].trim());
                boolean isFlaggedFraud = Boolean.parseBoolean(linha[10].trim());

                return new Transaction(Integer.parseInt(id), type, amount, customer, destination, isFraud, isFlaggedFraud);
        }
}
