package com.helpdesk.model;

import java.sql.Timestamp;

public record TicketComment(long ticketId, String author, String body, Timestamp createdAt) {}