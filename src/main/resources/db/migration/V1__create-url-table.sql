CREATE TABLE IF NOT EXISTS url (
    id BIGSERIAL PRIMARY KEY,
    hash VARCHAR(20) NOT NULL UNIQUE,
    original_url TEXT NOT NULL,
    access_count BIGINT NOT NULL DEFAULT 0,
    expires_at TIMESTAMP NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM pg_class c
        JOIN pg_namespace n ON n.oid = c.relnamespace
        WHERE c.relname = 'idx_url_hash'
          AND c.relkind = 'i'
    ) THEN
        CREATE UNIQUE INDEX idx_url_hash ON url(hash);
    END IF;
END
$$;

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM pg_class c
        JOIN pg_namespace n ON n.oid = c.relnamespace
        WHERE c.relname = 'idx_url_expires_at'
          AND c.relkind = 'i'
    ) THEN
        CREATE INDEX idx_url_expires_at ON url(expires_at);
    END IF;
END
$$;
