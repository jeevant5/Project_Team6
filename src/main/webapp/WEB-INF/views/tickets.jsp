<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List,com.helpdesk.model.Ticket" %>
<% boolean canManageTickets = Boolean.TRUE.equals(request.getAttribute("canManageTickets")); %>
<!doctype html>
<html>
<head><title>Helpdesk | Tickets</title><link rel="stylesheet" href="${pageContext.request.contextPath}/assets/app.css"></head>
<body>
<header><div><p class="eyebrow">NORTHSTAR IT OPERATIONS</p><h1>Helpdesk workspace</h1></div><span class="user"><%= session.getAttribute("displayName") %> - <%= session.getAttribute("role") %></span></header>
<main>
<section class="stats"><div><strong><%= ((int[])request.getAttribute("counts"))[0] %></strong><span>Open</span></div><div><strong><%= ((int[])request.getAttribute("counts"))[1] %></strong><span>In progress</span></div><div><strong><%= ((int[])request.getAttribute("counts"))[2] %></strong><span>Resolved</span></div><div><strong><%= ((int[])request.getAttribute("counts"))[3] %></strong><span>Closed</span></div></section>
<section class="layout">
<form class="panel" method="post" enctype="multipart/form-data"><h2>Raise a ticket</h2><label>Title<input name="title" maxlength="150" required></label><label>Description<textarea name="description" rows="6" required></textarea></label><label>Priority<select name="priority"><option>LOW</option><option selected>MEDIUM</option><option>HIGH</option></select></label><label>Attachment<input type="file" name="attachment"></label><button>Submit ticket</button></form>
<section><h2>Recent tickets</h2><div class="ticket-list">
<% for (Ticket ticket : (List<Ticket>)request.getAttribute("tickets")) { %>
<article class="ticket"><div class="ticket-top"><span class="status status-<%= ticket.status().toLowerCase() %>"><%= ticket.status() %></span><span class="priority"><%= ticket.priority() %></span></div><h3><%= ticket.title() %></h3><p><%= ticket.description() %></p><small>Opened by <%= ticket.creator() %> - <%= ticket.technician() %></small>
<% if (canManageTickets) { %><form method="post" action="ticket-action" class="actions"><input type="hidden" name="ticketId" value="<%= ticket.id() %>"><button name="action" value="assign" type="submit">Assign to me</button><input name="body" placeholder="Add an update"><button name="action" value="comment" type="submit">Comment</button><button name="action" value="resolve" type="submit">Resolve</button></form><% } %>
</article>
<% } %>
</div></section></section></main></body></html>
