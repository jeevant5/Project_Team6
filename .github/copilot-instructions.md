# Helpdesk Ticket Resolution System

- Java 25, Maven WAR, Jakarta Servlet 6, JSP, Apache Tomcat 10.1+.
- Oracle persistence must use JDBC through DAO classes.
- Keep controllers thin; put business rules in services and SQL in DAOs.
- Validate authorization and all user input server-side.
- Run `mvn clean package` before delivery.
