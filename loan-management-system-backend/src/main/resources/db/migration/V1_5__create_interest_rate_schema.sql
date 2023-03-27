CREATE TABLE interest_rate
(
    interest_rate_id   BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    interest_rate      DECIMAL(8, 5)                           NOT NULL,
    interest_rate_date date                                    NOT NULL,
    created_by         VARCHAR(255)                            NOT NULL,
    updated_by         VARCHAR(255),
    created_date       TIMESTAMP WITHOUT TIME ZONE             NOT NULL,
    updated_date       TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_interest_rate PRIMARY KEY (interest_rate_id)
);
