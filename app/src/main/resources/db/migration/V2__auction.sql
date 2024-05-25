CREATE TABLE auction
(
    id          SERIAL         NOT NULL,
    name        VARCHAR(60)    NOT NULL,
    starts_at   TIMESTAMP      NOT NULL,
    ends_at     TIMESTAMP      NOT NULL,
    description VARCHAR(120),
    price       DECIMAL(5, 2) NOT NULL,
    PRIMARY KEY (id)
);