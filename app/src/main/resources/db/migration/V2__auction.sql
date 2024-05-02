CREATE TABLE auction
(
    id          SERIAL          NOT NULL,
    name        VARCHAR(60)     NOT NULL,
    starts_at    TIMESTAMP       NOT NULL,
    ends_at      TIMESTAMP       NOT NULL,
    description VARCHAR(120)            ,
    price       NUMERIC(2)      NOT NULL,
    active      INDICATOR       NOT NULL,
    PRIMARY KEY (id)
);