--changeset init:1
CREATE TABLE IF NOT EXISTS chat
(
    id BIGINT PRIMARY KEY,
    created_at TIMESTAMP NOT NULL
);