package com.helpdesk.web;

import com.helpdesk.dao.TicketDao;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/ticket-action")
public class TicketActionServlet extends HttpServlet {
    private final TicketDao tickets = new TicketDao();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session=request.getSession(false); Object id=session == null ? null : session.getAttribute("userId");
        if (id == null) { response.sendRedirect("login"); return; }
        String role = (String) session.getAttribute("role");
        if (!"TECHNICIAN".equals(role) && !"ADMIN".equals(role)) { response.sendError(HttpServletResponse.SC_FORBIDDEN, "Only technicians can manage tickets"); return; }
        try {
            String action=request.getParameter("action");
            long ticketId=Long.parseLong(request.getParameter("ticketId"));
            String body=request.getParameter("body");
            String message;
            if ("assign".equals(action)) { tickets.assign(ticketId,(Long)id); message = "assigned"; }
            else if ("resolve".equals(action)) {
                if (body != null && !body.isBlank()) tickets.addComment(ticketId,(Long)id,body,"RESOLVED");
                else tickets.resolve(ticketId);
                message = "resolved";
            } else if ("comment".equals(action) && body != null && !body.isBlank()) { tickets.addComment(ticketId,(Long)id,body,"IN_PROGRESS"); message = "updated"; }
            else { response.sendError(HttpServletResponse.SC_BAD_REQUEST, "A comment is required"); return; }
            response.sendRedirect("tickets?message=" + message);
        }
        catch (Exception error) { throw new IOException(error); }
    }
}
