package org.example.trs2_lab.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;

@Service
public class DistributedTransactionService {

    private final DataSource mysqlDataSource;
    private final DataSource postgresDataSource;

    public DistributedTransactionService(
            @Qualifier("mysqlDataSource") DataSource mysqlDataSource,
            @Qualifier("postgresDataSource") DataSource postgresDataSource) {
        this.mysqlDataSource = mysqlDataSource;
        this.postgresDataSource = postgresDataSource;
    }

    public void executeDistributedTransaction(String mysqlQuery, String postgresQuery) {
        Connection mysqlConn = null;
        Connection postgresConn = null;

        try {
            mysqlConn = mysqlDataSource.getConnection();
            postgresConn = postgresDataSource.getConnection();

            mysqlConn.setAutoCommit(false);
            postgresConn.setAutoCommit(false);

            try (Statement ms = mysqlConn.createStatement();
                 Statement ps = postgresConn.createStatement()) {

                ms.executeUpdate(mysqlQuery);
                ps.executeUpdate(postgresQuery);

                mysqlConn.commit();
                postgresConn.commit();
            }

        } catch (Exception e) {
            try {
                if (mysqlConn != null) mysqlConn.rollback();
            } catch (Exception ignored) {}

            try {
                if (postgresConn != null) postgresConn.rollback();
            } catch (Exception ignored) {}

            throw new RuntimeException("Distributed transaction failed", e);

        } finally {
            try {
                if (mysqlConn != null) mysqlConn.close();
            } catch (Exception ignored) {}

            try {
                if (postgresConn != null) postgresConn.close();
            } catch (Exception ignored) {}
        }
    }
}
