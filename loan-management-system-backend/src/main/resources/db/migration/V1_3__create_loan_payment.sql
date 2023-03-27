CREATE TABLE loan_payment
(
    loan_payment_id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    loan_id         BIGINT                                  NOT NULL,
    gross_amount    DECIMAL                                 NOT NULL,
    net_amount      DECIMAL                                 NOT NULL,
    created_by      VARCHAR(255)                            NOT NULL,
    updated_by      VARCHAR(255),
    created_date    TIMESTAMP WITHOUT TIME ZONE             NOT NULL,
    updated_date    TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_loan_payment PRIMARY KEY (loan_payment_id)
);

ALTER TABLE loan_payment
    ADD CONSTRAINT FK_LOAN_PAYMENT_ON_LOAN FOREIGN KEY (loan_id) REFERENCES loan (loan_id);
