CREATE TABLE credit_facility
(
    credit_facility_id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    applicant_id       BIGINT                                  NOT NULL,
    credit_limit       DECIMAL(38, 2)                          NOT NULL,
    created_by         VARCHAR(255)                            NOT NULL,
    updated_by         VARCHAR(255),
    created_date       TIMESTAMP WITHOUT TIME ZONE             NOT NULL,
    updated_date       TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_credit_facility PRIMARY KEY (credit_facility_id)
);

ALTER TABLE credit_facility
    ADD CONSTRAINT FK_CREDIT_FACILITY_ON_APPLICANT FOREIGN KEY (applicant_id) REFERENCES applicant (applicant_id);
