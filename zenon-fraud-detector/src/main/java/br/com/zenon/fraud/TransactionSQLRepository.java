package br.com.zenon.fraud;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class TransactionSQLRepository implements TransactionRepositoryInterface {

    @Override
    public void save(Transaction transaction) {
        String sql = """
                    INSERT INTO transactions 
                    (step,`type`,amount,name_origin,old_balance_origin,new_balance_origin,name_recipient,old_balance_recipient,new_balance_recipient,is_fraud,is_flagged_fraud)
                    values
                    (?,?,?,?,?,?,?,?,?,?,?)
                """;

        try(Connection connection = ConnectionFactory.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1,  transaction.step());
            ps.setString(2, transaction.type().name());
            ps.setBigDecimal(3, transaction.amount());

            ps.setString(4, transaction.origin().name());
            ps.setBigDecimal(5, transaction.origin().newBalance());
            ps.setBigDecimal(6, transaction.origin().oldBalance());

            ps.setString(7, transaction.recipient().name());
            ps.setBigDecimal(8, transaction.recipient().newBalance());
            ps.setBigDecimal(9, transaction.recipient().oldBalance());

            ps.setBoolean(10, transaction.isFraud());
            ps.setBoolean(11, transaction.isFlaggedFraud());

            ps.execute();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar os dados.", e);
        }
    }


    @Override
    public Optional<Transaction> findByOriginName(String name) {
        String sql = """
                SELECT id, step, `type`, amount, name_origin, old_balance_origin, new_balance_origin, 
                    name_recipient, old_balance_recipient, new_balance_recipient, is_fraud, is_flagged_fraud
                FROM zenon.transactions
                WHERE name_origin = ?
                """;

        try(Connection connection = ConnectionFactory.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, name);
            try(ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {
                    Transaction transaction = mapResultSetToTransaction(rs);
                    IO.println(transaction);
                    return Optional.of(transaction);
                } else {
                    IO.println("Dados não encontrados");
                    return Optional.empty();
                }
            }
        } catch (Exception e){
            throw new RuntimeException("Erro ao buscar dados de transação", e);
        }
    }

    private Transaction mapResultSetToTransaction(ResultSet rs) {
        try {
            int step = rs.getInt("step");
            TypeEnum type = TypeEnum.valueOf(rs.getString("type"));
            BigDecimal amount = rs.getBigDecimal("amount");
            Customer origin = new Customer(rs.getString("name_origin"),rs.getBigDecimal("new_balance_origin"), rs.getBigDecimal("old_balance_origin"));
            Customer recipient = new Customer(rs.getString("name_recipient"),rs.getBigDecimal("new_balance_recipient"), rs.getBigDecimal("old_balance_recipient"));
            boolean isFraud = rs.getBoolean("is_fraud");
            boolean isFlaggedFraud = rs.getBoolean("is_flagged_fraud");

            return new Transaction(step, type, amount, origin, recipient, isFraud, isFlaggedFraud);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }




}
