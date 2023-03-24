DELETE FROM template_events;
DELETE FROM placeholder_keys;
DELETE FROM templates;

ALTER SEQUENCE template_events_id_seq RESTART WITH 1;
ALTER SEQUENCE templates_id_seq RESTART WITH 1;
ALTER SEQUENCE placeholder_keys_id_seq RESTART WITH 1;
