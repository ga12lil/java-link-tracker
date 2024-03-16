--changeset update_id_type:1
ALTER TABLE link
    ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY;
