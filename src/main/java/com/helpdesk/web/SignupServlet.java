package com.helpdesk.web;

import com.helpdesk.config.PasswordHasher;
import com.helpdesk.dao.UserDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/signup")
public class SignupServlet extends HttpServlet {
    private final UserDao userDao = new UserDao();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/signup.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = value(request, "username");
        String displayName = value(request, "displayName");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        String error = validate(username, displayName, password, confirmPassword);
        if (error != null) {
            request.setAttribute("error", error);
            request.getRequestDispatcher("/WEB-INF/views/signup.jsp").forward(request, response);
            return;
        }

        try {
            userDao.create(username, PasswordHasher.hash(password), displayName);
            response.sendRedirect(request.getContextPath() + "/login?registered=1");
        } catch (SQLException databaseError) {
            if (databaseError.getErrorCode() == 1) {
                request.setAttribute("error", "That username is already in use.");
                request.getRequestDispatcher("/WEB-INF/views/signup.jsp").forward(request, response);
                return;
            }
            throw new IOException("Database unavailable", databaseError);
        }
    }

    private static String value(HttpServletRequest request, String name) {
        String value = request.getParameter(name);
        return value == null ? "" : value.trim();
    }

    private static String validate(String username, String displayName, String password, String confirmPassword) {
        if (!username.matches("[A-Za-z0-9._-]{3,80}")) return "Username must be 3-80 letters, numbers, dots, underscores, or hyphens.";
        if (displayName.isBlank() || displayName.length() > 120) return "Display name is required and must be 120 characters or fewer.";
        if (password == null || password.length() < 8) return "Password must be at least 8 characters.";
        if (!password.equals(confirmPassword)) return "Passwords do not match.";
        return null;
    }
}