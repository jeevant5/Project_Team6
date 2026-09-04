package com.helpdesk.dao;

import com.helpdesk.config.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDao {
    public void create(String username, String passwordHash, String displayName) throws SQLException {
        String sql = "INSERT INTO app_users(username,password_hash,display_name,role) VALUES (?,?,?,'USER')";
        try (Connection connection = Database.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            statement.setString(2, passwordHash);
            statement.setString(3, displayName);
            statement.executeUpdate();
        }
    }
}