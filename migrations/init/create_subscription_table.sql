--changeset init:1
CREATE TABLE IF NOT EXISTS subscription
(
    chat_id BIGINT,
    link_id BIGINT,
    PRIMARY KEY (chat_id, link_id),
    CONSTRAINT fk_chat FOREIGN KEY (chat_id) REFERENCES chat (id),
    CONSTRAINT fk_link FOREIGN KEY (link_id) REFERENCES link (id)
);