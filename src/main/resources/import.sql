INSERT INTO user (username, password, email, status, created_at, updated_at) VALUES ('admin', '{noop}admin', 'admin@ticketplatform.org', 0, '2024-08-28 20:00:00', '2024-08-28 20:00:00'),('mario', '{noop}mario', 'mario@ticketplatform.org', 1, '2024-08-28 20:00:00', '2024-08-28 20:00:00');
INSERT INTO role (name) VALUES ('ADMIN'),('OPERATOR');
INSERT INTO tickets (title, description, category, status, created_at, updated_at, user_id) VALUES ('Aiuto', 'Non mi funziona', 'Software', 'da eseguire', '2024-08-28 20:00:00', '2024-08-28 20:00:00', 1),('Problema', 'Non si riesce ad installare un programma', 'Software', 'da eseguire', '2024-08-28 20:00:00', '2024-08-28 20:00:00', 1);
INSERT INTO user_roles (user_id, role_id) VALUES (1, 1),(2, 2);
