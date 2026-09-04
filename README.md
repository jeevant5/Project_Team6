# Helpdesk Ticket Resolution System

A Java 25 JSP/Servlet capstone application for submitting, assigning, discussing, and resolving IT support tickets.

## Stack
- Java 25, Jakarta Servlet 6, JSP, Apache Tomcat 10.1+
- Oracle Database 19c/23ai via JDBC
- Maven WAR packaging

## Run
1. Create an Oracle schema and run `database/schema.sql`.
2. Set `HELPDESK_DB_URL`, `HELPDESK_DB_USER`, and `HELPDESK_DB_PASSWORD` as environment variables.
3. Run `mvn clean package`.
4. Deploy `target/helpdesk.war` to Tomcat 10.1+.
5. Open `/helpdesk/login`.

The default demo accounts are documented in `database/schema.sql`; replace passwords before any shared deployment.

## Project docs
- `docs/architecture.md`: services, boundaries, and data flow
- `docs/api.md`: HTTP endpoints
- `docs/agile-plan.md`: sprint plan and acceptance criteria
- `docker-compose.yml`: local Oracle-compatible deployment notes
