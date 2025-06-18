-- Agregando la columna update
ALTER TABLE topics
    ADD COLUMN IF NOT EXISTS updated_at TIMESTAMP;

-- Crear function para actualizar el campo
CREATE OR REPLACE FUNCTION update_updated_at_column()
    RETURNS TRIGGER AS
$$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Crear el trigger que llama a la función antes de cada UPDATE
DROP TRIGGER IF EXISTS set_updated_at ON topics;

CREATE TRIGGER set_updated_at
    BEFORE UPDATE
    ON topics
    FOR EACH ROW
EXECUTE FUNCTION update_updated_at_column();