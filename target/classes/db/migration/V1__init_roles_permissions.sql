-- Insertar roles
INSERT INTO roles (role_name) VALUES
                                  ('ADMIN'),
                                  ('MODERATOR'),
                                  ('USER');

-- Insertar permisos
INSERT INTO permissions (name) VALUES
                                   ('TOPICO_CREAR'),
                                   ('TOPICO_EDITAR_PROPIO'),
                                   ('TOPICO_EDITAR_OTRO'),
                                   ('TOPICO_ELIMINAR_PROPIO'),
                                   ('TOPICO_ELIMINAR_OTRO'),
                                   ('COMENTARIO_CREAR'),
                                   ('COMENTARIO_ELIMINAR_PROPIO'),
                                   ('COMENTARIO_ELIMINAR_OTRO'),
                                   ('USUARIO_VER'),
                                   ('USUARIO_BLOQUEAR'),
                                   ('VOTO_CREAR'),
                                   ('CURSO_ADMIN');

-- Asociar permisos a roles

-- USER
INSERT INTO role_permissions (role_id, permission_id)
SELECT r.id, p.id FROM roles r, permissions p
WHERE r.role_name = 'USER' AND p.name IN (
                                          'TOPICO_CREAR',
                                          'TOPICO_EDITAR_PROPIO',
                                          'TOPICO_ELIMINAR_PROPIO',
                                          'COMENTARIO_CREAR',
                                          'COMENTARIO_ELIMINAR_PROPIO',
                                          'VOTO_CREAR'
    );

-- MODERATOR
INSERT INTO role_permissions (role_id, permission_id)
SELECT r.id, p.id FROM roles r, permissions p
WHERE r.role_name = 'MODERATOR' AND p.name IN (
                                               'TOPICO_CREAR',
                                               'TOPICO_EDITAR_PROPIO',
                                               'TOPICO_EDITAR_OTRO',
                                               'TOPICO_ELIMINAR_PROPIO',
                                               'TOPICO_ELIMINAR_OTRO',
                                               'COMENTARIO_CREAR',
                                               'COMENTARIO_ELIMINAR_PROPIO',
                                               'COMENTARIO_ELIMINAR_OTRO',
                                               'USUARIO_VER',
                                               'VOTO_CREAR'
    );

-- ADMIN
INSERT INTO role_permissions (role_id, permission_id)
SELECT r.id, p.id FROM roles r, permissions p
WHERE r.role_name = 'ADMIN'; -- Asigna todos los permisos a ADMIN
