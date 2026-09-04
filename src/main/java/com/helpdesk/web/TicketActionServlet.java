package com.helpdesk.web;

import com.helpdesk.dao.TicketDao;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/ticket-action")
public class TicketActionServlet extends HttpServlet {
    private final TicketDao tickets = new TicketDao();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session=request.getSession(); Object id=session.getAttribute("userId");
        if (id == null) { response.sendRedirect("login"); return; }
        try { String action=request.getParameter("action"); long ticketId=Long.parseLong(request.getParameter("ticketId")); if ("assign".equals(action)) tickets.assign(ticketId,(Long)id); else tickets.addComment(ticketId,(Long)id,request.getParameter("body"),"resolve".equals(action)?"RESOLVED":"IN_PROGRESS"); response.sendRedirect("tickets"); }
        catch (Exception error) { throw new IOException(error); }
    }
}
