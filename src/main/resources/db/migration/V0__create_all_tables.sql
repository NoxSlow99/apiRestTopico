-- ====================
-- AUTENTICACIÓN
-- ====================

CREATE TABLE users (
                       id BIGSERIAL PRIMARY KEY,
                       name VARCHAR(100) NOT NULL,
                       email VARCHAR(100) UNIQUE NOT NULL,
                       username VARCHAR(50) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL,

                       is_enabled BOOLEAN DEFAULT TRUE,
                       account_no_expired BOOLEAN DEFAULT TRUE,
                       account_no_locked BOOLEAN DEFAULT TRUE,
                       credential_no_expired BOOLEAN DEFAULT TRUE
);

CREATE TABLE roles (
                       id BIGSERIAL PRIMARY KEY,
                       role_name VARCHAR(30) UNIQUE NOT NULL
);

CREATE TABLE permissions (
                             id BIGSERIAL PRIMARY KEY,
                             name VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE user_roles (
                            user_id BIGINT NOT NULL,
                            role_id BIGINT NOT NULL,
                            PRIMARY KEY (user_id, role_id),
                            FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                            FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);

CREATE TABLE role_permissions (
                                  role_id BIGINT NOT NULL,
                                  permission_id BIGINT NOT NULL,
                                  PRIMARY KEY (role_id, permission_id),
                                  FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE,
                                  FOREIGN KEY (permission_id) REFERENCES permissions(id) ON DELETE CASCADE
);

-- ====================
-- FORO
-- ====================

-- Cursos (relacionados con tópicos)
CREATE TABLE courses (
                         id BIGSERIAL PRIMARY KEY,
                         name VARCHAR(100) UNIQUE NOT NULL,
                         descripcion VARCHAR(500)
);

-- Tópicos
CREATE TABLE topics (
                        id BIGSERIAL PRIMARY KEY,
                        titulo VARCHAR(255) UNIQUE NOT NULL,
                        mensaje TEXT UNIQUE NOT NULL,
                        fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        estatus BOOLEAN DEFAULT TRUE,
                        autor_id BIGINT NOT NULL,
                        curso_id BIGINT,

                        FOREIGN KEY (autor_id) REFERENCES users(id),
                        FOREIGN KEY (curso_id) REFERENCES courses(id)
);

-- Comentarios
CREATE TABLE comments (
                          id BIGSERIAL PRIMARY KEY,
                          contenido TEXT NOT NULL,
                          fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          topico_id BIGINT NOT NULL,
                          autor_id BIGINT NOT NULL,

                          FOREIGN KEY (topico_id) REFERENCES topics(id) ON DELETE CASCADE,
                          FOREIGN KEY (autor_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Votos
CREATE TABLE votes (
                       id BIGSERIAL PRIMARY KEY,
                       positivo BOOLEAN NOT NULL,
                       fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       usuario_id BIGINT NOT NULL,
                       topico_id BIGINT NOT NULL,

                       UNIQUE (usuario_id, topico_id), -- Solo un voto por usuario por tópico
                       FOREIGN KEY (usuario_id) REFERENCES users(id) ON DELETE CASCADE,
                       FOREIGN KEY (topico_id) REFERENCES topics(id) ON DELETE CASCADE
);
