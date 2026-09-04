package com.helpdesk.dao;

import com.helpdesk.config.Database;
import com.helpdesk.model.Ticket;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TicketDao {
    public long create(long userId, String title, String description, String priority,
                       String fileName, String contentType, InputStream attachment) throws SQLException {
        String sql = "INSERT INTO tickets(user_id,title,description,priority,attachment_name,attachment_type,attachment_data) VALUES (?,?,?,?,?,?,?)";
        try (Connection connection = Database.getConnection(); PreparedStatement statement = connection.prepareStatement(sql, new String[]{"TICKET_ID"})) {
            statement.setLong(1, userId); statement.setString(2, title); statement.setString(3, description);
            statement.setString(4, priority); statement.setString(5, fileName); statement.setString(6, contentType);
            if (attachment == null) statement.setNull(7, Types.BLOB); else statement.setBlob(7, attachment);
            statement.executeUpdate();
            try (ResultSet keys = statement.getGeneratedKeys()) { keys.next(); return keys.getLong(1); }
        }
    }

    public List<Ticket> findAll() throws SQLException {
        String sql = "SELECT t.ticket_id,t.title,t.description,t.priority,t.status,u.display_name,COALESCE(a.display_name,'Unassigned'),t.created_at FROM tickets t JOIN app_users u ON u.user_id=t.user_id LEFT JOIN app_users a ON a.user_id=t.tech_id ORDER BY t.created_at DESC";
        List<Ticket> tickets = new ArrayList<>();
        try (Connection connection = Database.getConnection(); PreparedStatement statement = connection.prepareStatement(sql); ResultSet result = statement.executeQuery()) {
            while (result.next()) tickets.add(new Ticket(result.getLong(1),result.getString(2),result.getString(3),result.getString(4),result.getString(5),result.getString(6),result.getString(7),result.getTimestamp(8)));
        }
        return tickets;
    }

    public void assign(long ticketId, long technicianId) throws SQLException {
        try (Connection connection = Database.getConnection(); PreparedStatement statement = connection.prepareStatement("UPDATE tickets SET tech_id=?,status='IN_PROGRESS',updated_at=CURRENT_TIMESTAMP WHERE ticket_id=?")) {
            statement.setLong(1, technicianId); statement.setLong(2, ticketId); statement.executeUpdate();
        }
    }

    public void addComment(long ticketId, long userId, String body, String status) throws SQLException {
        try (Connection connection = Database.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement comment = connection.prepareStatement("INSERT INTO ticket_comments(ticket_id,user_id,body) VALUES (?,?,?)"); PreparedStatement update = connection.prepareStatement("UPDATE tickets SET status=?,updated_at=CURRENT_TIMESTAMP WHERE ticket_id=?")) {
                comment.setLong(1,ticketId); comment.setLong(2,userId); comment.setString(3,body); comment.executeUpdate();
                update.setString(1,status); update.setLong(2,ticketId); update.executeUpdate(); connection.commit();
            } catch (SQLException error) { connection.rollback(); throw error; }
        }
    }

    public int[] statusCounts() throws SQLException {
        int[] counts = new int[4];
        try (Connection connection = Database.getConnection(); PreparedStatement statement = connection.prepareStatement("SELECT status,COUNT(*) FROM tickets GROUP BY status"); ResultSet result = statement.executeQuery()) {
            while (result.next()) { int index = switch (result.getString(1)) { case "OPEN" -> 0; case "IN_PROGRESS" -> 1; case "RESOLVED" -> 2; default -> 3; }; counts[index] = result.getInt(2); }
        }
        return counts;
    }
}
