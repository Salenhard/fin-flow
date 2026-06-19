CREATE TABLE users
(
    id            UUID         NOT NULL,
    username      VARCHAR(255) NOT NULL,
    email         VARCHAR(255) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    role          VARCHAR(255) NOT NULL,
    created_at    BYTEA        NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE users
    ADD CONSTRAINT UC_USERS_EMAIL UNIQUE (email);

ALTER TABLE users
    ADD CONSTRAINT UC_USERS_USERNAME UNIQUE (username);