CREATE TABLE url (
    id BIGSERIAL PRIMARY KEY,
    hash VARCHAR(20) NOT NULL UNIQUE,
    original_url TEXT NOT NULL,
    access_count BIGINT NOT NULL DEFAULT 0,
    expires_at TIMESTAMP NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE UNIQUE INDEX idx_url_hash ON url(hash);

CREATE INDEX idx_url_expires_at ON url(expires_at);
