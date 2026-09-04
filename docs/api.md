# HTTP API

| Method | Path | Purpose |
|---|---|---|
| GET/POST | `/login` | Authenticate a user session |
| GET | `/tickets` | Show ticket list and status aggregates |
| POST | `/tickets` | Create a ticket; accepts multipart attachment |
| POST | `/ticket-action` | Assign, comment, or resolve a ticket (technicians/admins only) |

All ticket endpoints require a session. Users can submit tickets; technicians and admins can assign, comment, and resolve them.
