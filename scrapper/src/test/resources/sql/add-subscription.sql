INSERT INTO chat (id) VALUES (12);
INSERT INTO link (url) VALUES ('link');
INSERT INTO subscription (chat_id, link_id) VALUES (12, (SELECT id FROM link WHERE url='link'));
