DROP TABLE IF EXISTS templates;
DROP TABLE IF EXISTS placeholder_keys;

DROP SEQUENCE IF EXISTS templates_id_seq;
DROP SEQUENCE IF EXISTS placeholder_keys_id_seq;

CREATE TABLE templates
(
    id      BIGINT      PRIMARY KEY,
    name    VARCHAR(64) NOT NULL,
    payload TEXT        NOT NULL
);

CREATE TABLE placeholder_keys
(
    id              BIGINT      PRIMARY KEY,
    placeholder_key VARCHAR(32) NOT NULL,
    template_id     BIGINT      NOT NULL,
    CONSTRAINT templates_fk FOREIGN KEY(template_id) REFERENCES templates(id)
);

CREATE INDEX placeholder_keys_template_id_idx ON placeholder_keys(template_id);

CREATE SEQUENCE templates_id_seq START WITH 1 INCREMENT 1;
CREATE SEQUENCE placeholder_keys_id_seq START WITH 1 INCREMENT 1;
