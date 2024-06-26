ALTER TABLE auction
    ADD COLUMN IF NOT EXISTS winner_id INTEGER;

ALTER TABLE auction
    ADD COLUMN IF NOT EXISTS winner_email VARCHAR(60);

ALTER TABLE auction
    ADD COLUMN IF NOT EXISTS owner_id INTEGER;

ALTER TABLE auction
    RENAME COLUMN price TO start_price;

ALTER TABLE auction
    ADD COLUMN IF NOT EXISTS current_price DECIMAL(5, 2);

