package com.helpdesk.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.sql.Timestamp;
import org.junit.jupiter.api.Test;

class TicketTest {
    @Test
    void exposesAllTicketValues() {
        Timestamp createdAt = Timestamp.valueOf("2026-09-03 12:00:00");
        Ticket ticket = new Ticket(42L, "Printer issue", "Cannot print", "HIGH", "OPEN",
                "Alice", "Bob", createdAt);

        assertEquals(42L, ticket.id());
        assertEquals("Printer issue", ticket.title());
        assertEquals("Cannot print", ticket.description());
        assertEquals("HIGH", ticket.priority());
        assertEquals("OPEN", ticket.status());
        assertEquals("Alice", ticket.creator());
        assertEquals("Bob", ticket.technician());
        assertEquals(createdAt, ticket.createdAt());
    }

    @Test
    void comparesTicketsByValue() {
        Timestamp createdAt = Timestamp.valueOf("2026-09-03 12:00:00");
        Ticket first = new Ticket(42L, "Printer issue", "Cannot print", "HIGH", "OPEN",
                "Alice", "Bob", createdAt);
        Ticket second = new Ticket(42L, "Printer issue", "Cannot print", "HIGH", "OPEN",
                "Alice", "Bob", createdAt);

        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
    }

    @Test
    void permitsNullableOptionalValues() {
        Ticket ticket = new Ticket(7L, "New ticket", "Details", "LOW", "OPEN",
                "Alice", null, null);

        assertNull(ticket.technician());
        assertNull(ticket.createdAt());
    }
}