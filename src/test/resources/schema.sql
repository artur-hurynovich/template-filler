DROP TABLE IF EXISTS template_events;
DROP TABLE IF EXISTS placeholder_keys;
DROP TABLE IF EXISTS templates;

DROP SEQUENCE IF EXISTS template_events_id_seq;
DROP SEQUENCE IF EXISTS templates_id_seq;
DROP SEQUENCE IF EXISTS placeholder_keys_id_seq;

CREATE TABLE template_events
(
    id                  BIGINT      PRIMARY KEY,
    type                VARCHAR(16) NOT NULL,
    template_id         BIGINT      NOT NULL,
    template_name       VARCHAR(64),
    template_payload    TEXT,
    date_time           TIMESTAMP   NOT NULL
);

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

CREATE INDEX template_events_template_id_idx ON template_events(template_id);
CREATE INDEX templates_name_idx ON templates(name);
CREATE INDEX placeholder_keys_template_id_idx ON placeholder_keys(template_id);

CREATE SEQUENCE template_events_id_seq START WITH 1 INCREMENT 1;
CREATE SEQUENCE templates_id_seq START WITH 1 INCREMENT 1;
CREATE SEQUENCE placeholder_keys_id_seq START WITH 1 INCREMENT 1;
