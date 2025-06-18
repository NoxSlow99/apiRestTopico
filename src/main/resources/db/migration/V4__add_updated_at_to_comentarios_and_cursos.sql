-- 1. Agregar columna updated_at a comentarios y cursos si no existen
ALTER TABLE comments
    ADD COLUMN IF NOT EXISTS updated_at TIMESTAMP;

ALTER TABLE courses
    ADD COLUMN IF NOT EXISTS updated_at TIMESTAMP,
    ADD COLUMN IF NOT EXISTS created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;

-- 2. Reutilizar la misma función si ya existe (compartida entre tablas)
CREATE OR REPLACE FUNCTION update_updated_at_column()
    RETURNS TRIGGER AS
$$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- 3. Crear o reemplazar triggers para comentarios
DROP TRIGGER IF EXISTS set_updated_at_comentarios ON comments;

CREATE TRIGGER set_updated_at_comentarios
    BEFORE UPDATE
    ON comments
    FOR EACH ROW
EXECUTE FUNCTION update_updated_at_column();

-- 4. Crear o reemplazar triggers para cursos
DROP TRIGGER IF EXISTS set_updated_at_cursos ON courses;

CREATE TRIGGER set_updated_at_cursos
    BEFORE UPDATE
    ON courses
    FOR EACH ROW
EXECUTE FUNCTION update_updated_at_column();
