# HTTP API

| Method | Path | Purpose |
|---|---|---|
| GET/POST | `/login` | Authenticate a user session |
| GET | `/tickets` | Show ticket list and status aggregates |
| POST | `/tickets` | Create a ticket; accepts multipart attachment |
| POST | `/ticket-action` | Assign, comment, or resolve a ticket |

All ticket endpoints require a session. In production, add CSRF tokens, password hashing, and role checks at the authorization boundary.
