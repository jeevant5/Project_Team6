package com.helpdesk.web;

import com.helpdesk.config.Database;
import com.helpdesk.config.PasswordHasher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request,response); }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String sql = "SELECT user_id,display_name,role,password_hash FROM app_users WHERE username=?";
        try (Connection connection = Database.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, request.getParameter("username"));
            try (ResultSet result = statement.executeQuery()) {
                if (result.next() && PasswordHasher.matches(request.getParameter("password"), result.getString(4))) { HttpSession session=request.getSession(true); session.setAttribute("userId",result.getLong(1)); session.setAttribute("displayName",result.getString(2)); session.setAttribute("role",result.getString(3)); response.sendRedirect(request.getContextPath()+"/tickets"); return; }
            }
        } catch (SQLException error) { throw new IOException("Database unavailable", error); }
        response.sendRedirect(request.getContextPath()+"/login?error=1");
    }
}
