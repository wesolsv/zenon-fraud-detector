package br.com.zenon.fraud;

public enum TypeEnum {
    CASH_IN, CASH_OUT, DEBIT, PAYMENT, TRANSFER;

    public static boolean validateEnum(String valor) {
        if (valor == null) return false;
        for (TypeEnum type : values()) {
            if (type.name().equalsIgnoreCase(valor)) return true;
        }
        return false;
    }
}