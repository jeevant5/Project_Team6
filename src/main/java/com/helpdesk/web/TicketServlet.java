package com.helpdesk.web;

import com.helpdesk.dao.TicketDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/tickets")
@MultipartConfig(maxFileSize=10_000_000)
public class TicketServlet extends HttpServlet {
    private final TicketDao tickets = new TicketDao();
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) { response.sendRedirect("login"); return; }
        try { request.setAttribute("tickets", tickets.findAll()); request.setAttribute("counts", tickets.statusCounts()); request.setAttribute("canManageTickets", isTicketManager(session)); request.getRequestDispatcher("/WEB-INF/views/tickets.jsp").forward(request,response); }
        catch (Exception error) { throw new ServletException(error); }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) { response.sendRedirect("login"); return; }
        try { Part file=request.getPart("attachment"); tickets.create((Long)session.getAttribute("userId"), request.getParameter("title"), request.getParameter("description"), request.getParameter("priority"), file == null ? null : file.getSubmittedFileName(), file == null ? null : file.getContentType(), file == null ? null : file.getInputStream()); response.sendRedirect("tickets"); }
        catch (Exception error) { throw new IOException(error); }
    }

    private static boolean isTicketManager(HttpSession session) {
        String role = (String) session.getAttribute("role");
        return "TECHNICIAN".equals(role) || "ADMIN".equals(role);
    }
}
