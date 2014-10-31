INSERT INTO authority (name) VALUES ('USER');
INSERT INTO authority (name) VALUES ('ADMIN');
INSERT INTO user (username, password, authority_id) VALUES ('admin', 'admin', 2);
INSERT INTO user (username, password, authority_id) VALUES ('joachim', 'pass1', 1);
INSERT INTO user (username, password, authority_id) VALUES ('sybille', 'pass1', 1);
INSERT INTO user (username, password, authority_id) VALUES ('claudia', 'pass1', 1);