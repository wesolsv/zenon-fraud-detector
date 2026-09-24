package br.com.zenon.util;

import br.com.zenon.fraud.Transaction;

import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class TransactionIngestor {


    public static final int MAX_TRANSACTIONS = 10_000;

    public static List<Transaction> read(String nomeArquivo) {
        List<Transaction> transactions  = new ArrayList<>();
        List<String> lines;
        AtomicInteger contador = new AtomicInteger();
        try {
            lines = Files.readAllLines(Paths.get(nomeArquivo));
            return lines.stream()
                    .skip(1)
                    .limit(MAX_TRANSACTIONS)
                    .map(l -> Transaction.parseTransacao(l, contador))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .toList();
        }catch (Exception ex){
            System.err.println("Erro de dados: " + ex.getMessage());
        }
        IO.println(contador.toString());
        return transactions;
    }


//    public static List<Transaction> readFileNIO2(String nomeArquivo) {
//        Path path = Paths.get(nomeArquivo);
//        List<Transaction> transactions;
//        try{
//            List<String> strings = Files.readAllLines(path);
//            transactions = strings.stream()
//                    .skip(1)
//                    .limit(1001)
//                    .map(line -> Transaction.montaTransacao(line.trim().split(",")))
//                    .toList();
//        } catch (Exception ex){
//            throw new RuntimeException("Erro na leitura de arquivo", ex);
//        }
//
//        return transactions;
//    }

//    public static List<Transaction> readFileNIO(String nomeArquivo) {
//        List<Transaction> lista = new ArrayList<>();
//        Path path = Paths.get(nomeArquivo);
//        StringBuilder dados = new StringBuilder();
//        int contador = 0;
//
//        try(FileChannel channel = FileChannel.open(path, StandardOpenOption.READ)){
//            ByteBuffer buffer = ByteBuffer.allocate(8192);
//
//            while (channel.read(buffer) != -1) {
//                buffer.flip();
//                dados.append(StandardCharsets.UTF_8.decode(buffer));
//                buffer.clear();
//
//                int idx;
//                while((idx = dados.indexOf("\n")) != -1){
//                    contador++;
//                    String linha = dados.substring(0, idx);
//                    dados.delete(0, idx + 1);
//                    if (contador <= 1001 && contador > 1){
//                        if(linha.isBlank()) continue;
//                        lista.add(Transaction.montaTransacao(linha.trim().split(",")));
//                    }
//                }
//
//                if(!dados.isEmpty()){
//                    String ultimaLinha = dados.toString().trim();
//                    if(ultimaLinha.isBlank()){
//                        lista.add(Transaction.montaTransacao(ultimaLinha.trim().split(",")));
//                    }
//                }
//            }
//        } catch (Exception ex){
//            throw new RuntimeException("Erro na leitura de arquivo", ex);
//        }
//        return lista;
//    }
}
