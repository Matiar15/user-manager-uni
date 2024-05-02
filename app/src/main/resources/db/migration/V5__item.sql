CREATE TABLE item
(
    id          SERIAL          NOT NULL,
    name        VARCHAR(60)     NOT NULL,
    id_auction  INTEGER         NOT NULL,
    id_category INTEGER         NOT NULL,
    price       NUMERIC(2)      NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_category
        FOREIGN KEY(id_category)
            REFERENCES category(id),
    CONSTRAINT fk_auction
        FOREIGN KEY(id_auction)
            REFERENCES auction(id)
);
