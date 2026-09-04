package com.helpdesk.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Database {
    private Database() {}

    public static Connection getConnection() throws SQLException {
        String url = System.getenv().getOrDefault("HELPDESK_DB_URL", "jdbc:oracle:thin:@localhost:1521/FREEPDB1");
        String user = System.getenv().getOrDefault("HELPDESK_DB_USER", "helpdesk");
        String password = System.getenv().getOrDefault("HELPDESK_DB_PASSWORD", "helpdesk");
        try {
            Class.forName("oracle.jdbc.OracleDriver");
        } catch (ClassNotFoundException error) {
            throw new SQLException("Oracle JDBC driver unavailable", error);
        }
        return DriverManager.getConnection(url, user, password);
    }
}
