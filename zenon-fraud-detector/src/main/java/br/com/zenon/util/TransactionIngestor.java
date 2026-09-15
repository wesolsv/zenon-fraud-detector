package br.com.zenon.util;

import br.com.zenon.fraud.Transaction;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class TransactionIngestor {

    public static List<Transaction> buscaTransacoes(String nomeArquivo) throws IOException {
        List<Transaction> lista = new ArrayList<>();
        Path path = Paths.get(nomeArquivo);
        String linha;
        int contador = 0;
        String[] colunasValores;

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            while((linha = reader.readLine()) != null && contador <= 1000) {
                if(linha.trim().equals("")) continue;
                contador++;

                if(contador <= 1000){
                    colunasValores = linha.split(",");
                    if(contador != 1) {
                        Transaction transaction = Transaction.montaTransacao(colunasValores);
                        lista.add(transaction);
                    }
                }
            }
        }

        return lista;
    }
}
