CREATE TABLE app_users (
  user_id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  username VARCHAR2(80) UNIQUE NOT NULL,
  password_hash VARCHAR2(255) NOT NULL,
  display_name VARCHAR2(120) NOT NULL,
  role VARCHAR2(20) DEFAULT 'USER' NOT NULL CHECK (role IN ('USER','TECHNICIAN','ADMIN'))
);

CREATE TABLE tickets (
  ticket_id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  user_id NUMBER NOT NULL REFERENCES app_users(user_id),
  tech_id NUMBER REFERENCES app_users(user_id),
  title VARCHAR2(150) NOT NULL,
  description CLOB NOT NULL,
  priority VARCHAR2(10) DEFAULT 'MEDIUM' NOT NULL CHECK (priority IN ('LOW','MEDIUM','HIGH')),
  status VARCHAR2(20) DEFAULT 'OPEN' NOT NULL CHECK (status IN ('OPEN','IN_PROGRESS','RESOLVED','CLOSED')),
  attachment_name VARCHAR2(255),
  attachment_type VARCHAR2(120),
  attachment_data BLOB,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE TABLE ticket_comments (
  comment_id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  ticket_id NUMBER NOT NULL REFERENCES tickets(ticket_id) ON DELETE CASCADE,
  user_id NUMBER NOT NULL REFERENCES app_users(user_id),
  body VARCHAR2(2000) NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE TABLE ticket_feedback (
  feedback_id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  ticket_id NUMBER UNIQUE NOT NULL REFERENCES tickets(ticket_id) ON DELETE CASCADE,
  rating NUMBER(1) CHECK (rating BETWEEN 1 AND 5),
  notes VARCHAR2(1000),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

INSERT INTO app_users(username,password_hash,display_name,role) VALUES ('admin','CHANGE_ME','System Administrator','ADMIN');
INSERT INTO app_users(username,password_hash,display_name,role) VALUES ('tech','CHANGE_ME','Support Technician','TECHNICIAN');
INSERT INTO app_users(username,password_hash,display_name,role) VALUES ('user','CHANGE_ME','Demo User','USER');
COMMIT;
