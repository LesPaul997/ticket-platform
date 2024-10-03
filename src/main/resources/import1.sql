INSERT INTO user (user_id, username, password, email, status, created_at, updated_at) VALUES (1, 'admin', '{noop}q', 'admin@ticketplatform.org', 0, '2024-08-28 20:00:00', '2024-08-28 20:00:00'),(2, 'mario', '{noop}w', 'mario@ticketplatform.org', 1, '2024-08-28 20:00:00', '2024-08-28 20:00:00');
INSERT INTO role (role_id, name) VALUES (1, 'ADMIN'),(2, 'OPERATOR');
INSERT INTO tickets (title, description, category, status, created_at, updated_at, user_id) VALUES ('Aiuto', 'Non mi funziona', 'Hardware', 'da eseguire', '2024-08-28 20:00:00', '2024-08-28 20:00:00', 2),('Problema', 'Non si riesce ad installare un programma', 'Software', 'da eseguire', '2024-08-28 20:00:00', '2024-08-28 20:00:00', 2);
INSERT INTO user_roles (user_id, roles_id) VALUES (1, 1),(2, 2);
