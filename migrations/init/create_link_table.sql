--changeset init:1
CREATE TABLE IF NOT EXISTS link
(
    id BIGINT PRIMARY KEY,
    url TEXT UNIQUE NOT NULL,
    updated_at TIMESTAMP DEFAULT now() NOT NULL
);