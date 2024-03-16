--changeset init:1
ALTER TABLE subscription
DROP CONSTRAINT fk_chat,
ADD CONSTRAINT fk_chat FOREIGN KEY (chat_id) REFERENCES chat (id) ON DELETE CASCADE;

ALTER TABLE subscription
DROP CONSTRAINT fk_link,
ADD CONSTRAINT fk_link FOREIGN KEY (link_id) REFERENCES link (id) ON DELETE CASCADE;
