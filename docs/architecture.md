# Architecture

The WAR is organized as a modular monolith with microservice-ready boundaries: authentication, ticket management, discussion, feedback, and reporting. Servlets are HTTP adapters, DAOs own Oracle SQL, and models carry typed data between layers. The ticket workflow is `OPEN -> IN_PROGRESS -> RESOLVED -> CLOSED`; assignment and comments are transactional operations.

For production extraction, `TicketDao` and `TicketActionServlet` can become a Ticket service, while reporting can consume ticket events. Attachments are currently stored as Oracle BLOBs. Configuration is environment-driven to follow 12-factor deployment principles.
