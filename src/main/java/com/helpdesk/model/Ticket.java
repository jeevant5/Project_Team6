package com.helpdesk.model;

import java.sql.Timestamp;

public record Ticket(long id, String title, String description, String priority, String status,
                     String creator, String technician, Timestamp createdAt) {}
