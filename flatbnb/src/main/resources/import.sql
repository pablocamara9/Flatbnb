-- Inserción de usuarios
INSERT INTO user_entity (id, username, password, nombre, apellidos, email, telefono, enabled, activation_token, created_at)
VALUES
-- Admin
(gen_random_uuid(), 'admin', '{bcrypt}$2a$12$kEeH.BiaTOopCQA0kf7Oyuy.I0U4W5G2U0EXt0RFqNnCfhmrIwUay', 'Admin', 'Admin', 'admin1@example.com', '600000001', true, null, now()),

-- Clientes (USER)
(gen_random_uuid(), 'cliente1', '{bcrypt}$2a$12$HudIWopmJk80ZoZ4XbPdF.IMy5wD/ooHl79eivxGt/NYylK.wsbSa', 'Carlos', 'Ramírez', 'cliente1@example.com', '600000002', true, null, now()),
(gen_random_uuid(), 'cliente2', '{bcrypt}$2a$12$pmDZpOAbFAnYqVuOlDZRmu97BCsoZLSeZEFrhpDJ5jfSzMod41lwm', 'Lucía', 'Gómez', 'cliente2@example.com', '600000003', true, null, now()),
(gen_random_uuid(), 'cliente3', '{bcrypt}$2a$12$GQsPapaIyHBH5zPKSN2aMugHLFuzDa3anZYbBQIJ.9TYUt13kO4KW', 'Miguel', 'López', 'cliente3@example.com', '600000004', true, null, now()),
(gen_random_uuid(), 'cliente4', '{bcrypt}$2a$12$x9nj7yjDyGwFzaZVn6yrKONSAN551krHrkMn9Tx.RyzY0Z7ejJHb.', 'Sofía', 'Martínez', 'cliente4@example.com', '600000005', true, null, now()),
(gen_random_uuid(), 'cliente5', '{bcrypt}$2a$12$aMBaTlBgbwXYJToDn8VkF.IogfarO7kA4qLrnia.WukfpFrjwKOYq', 'Andrés', 'Fernández', 'cliente5@example.com', '600000006', true, null, now()),

-- Propietarios
(gen_random_uuid(), 'propietario1', '{bcrypt}$2a$12$fA7Ej3EBKllXrsvF8YpqdeydYUxz6vWJFpX9vs5ArqoHJ4XZg8bBG', 'Ana', 'Torres', 'propietario1@example.com', '600000007', true, null, now()),
(gen_random_uuid(), 'propietario2', '{bcrypt}$2a$12$a/.dFfTTAGOZBjJ7hYb/Dec0/FD3.O3kM7i3tJ1afl/WChecLgkmq', 'Javier', 'Santos', 'propietario2@example.com', '600000008', true, null, now()),
(gen_random_uuid(), 'propietario3', '{bcrypt}$2a$12$FxzA/fcOqxCK.HP5Ggrav.4ofKkYrV94xZEDQXwbbj9xRCmoa8wd6', 'Marina', 'Delgado', 'propietario3@example.com', '600000009', true, null, now()),
(gen_random_uuid(), 'propietario4', '{bcrypt}$2a$12$OiagGFPL24hX.Jaw.7tPwe5IXPXEoNU/daHak5ATWVzzwxhbLo8uC', 'Luis', 'Vega', 'propietario4@example.com', '600000010', true, null, now()),
(gen_random_uuid(), 'propietario5', '{bcrypt}$2a$12$oaUh3VlrpnzzxKiRHucqw.gAo5/PHs5bK.MdrOe0gSJxTNHyKMFk.', 'Paula', 'Moreno', 'propietario5@example.com', '600000011', true, null, now());

-- Inserción de roles
-- Admin
INSERT INTO user_roles (user_id, roles)
VALUES ((SELECT id FROM user_entity WHERE username = 'admin'), 0);

-- Clientes
INSERT INTO user_roles (user_id, roles)
VALUES ((SELECT id FROM user_entity WHERE username = 'cliente1'), 1),
       ((SELECT id FROM user_entity WHERE username = 'cliente2'), 1),
       ((SELECT id FROM user_entity WHERE username = 'cliente3'), 1),
       ((SELECT id FROM user_entity WHERE username = 'cliente4'), 1),
       ((SELECT id FROM user_entity WHERE username = 'cliente5'), 1);

-- Propietarios
INSERT INTO user_roles (user_id, roles)
VALUES ((SELECT id FROM user_entity WHERE username = 'propietario1'), 2),
       ((SELECT id FROM user_entity WHERE username = 'propietario2'), 2),
       ((SELECT id FROM user_entity WHERE username = 'propietario3'), 2),
       ((SELECT id FROM user_entity WHERE username = 'propietario4'), 2),
       ((SELECT id FROM user_entity WHERE username = 'propietario5'), 2);

-- Tabla propietario con valoraciones (hereda de users)
INSERT INTO propietario (id, valoracion)
VALUES ((SELECT id FROM user_entity WHERE username = 'propietario1'), 4.3),
       ((SELECT id FROM user_entity WHERE username = 'propietario2'), 4.7),
       ((SELECT id FROM user_entity WHERE username = 'propietario3'), 3.9),
       ((SELECT id FROM user_entity WHERE username = 'propietario4'), 4.5),
       ((SELECT id FROM user_entity WHERE username = 'propietario5'), 4.0);

-- Insertar 7 pisos
INSERT INTO piso (id, direccion, metros_cuadrados, num_habitaciones, observaciones, propietario_id)
VALUES (gen_random_uuid(), 'Calle Falsa 123', 80.0, 3, 'Piso con mucha luz', (SELECT id FROM user_entity WHERE username = 'propietario1')),
       (gen_random_uuid(), 'Av. Siempre Viva 742', 65.0, 2, 'Recién reformado', (SELECT id FROM user_entity WHERE username = 'propietario1')),
       (gen_random_uuid(), 'Calle Luna 12', 90.0, 4, 'Ideal para familias', (SELECT id FROM user_entity WHERE username = 'propietario2')),
       (gen_random_uuid(), 'Calle Sol 8', 70.0, 2, 'Buena comunicación', (SELECT id FROM user_entity WHERE username = 'propietario2')),
       (gen_random_uuid(), 'Calle Mar 33', 55.0, 1, 'Perfecto para una persona sola', (SELECT id FROM user_entity WHERE username = 'propietario3')),
       (gen_random_uuid(), 'Av. Montaña 45', 110.0, 5, 'Con jardín y terraza', (SELECT id FROM user_entity WHERE username = 'propietario4')),
       (gen_random_uuid(), 'Calle Río 27', 60.0, 2, 'Céntrico y acogedor', (SELECT id FROM user_entity WHERE username = 'propietario5'));

-- Insertar 7 anuncios (uno por piso)
INSERT INTO anuncio (id, descripcion, precio, url_imagen, piso_id, propietario_id)
VALUES (gen_random_uuid(), 'Amplio piso ideal para parejas jóvenes.', 750.00, 'https://example.com/img1.jpg', (SELECT id FROM piso WHERE direccion = 'Calle Falsa 123'), (SELECT id FROM user_entity WHERE username = 'propietario1')),
       (gen_random_uuid(), 'Luminoso apartamento, perfecto para estudiantes.', 600.00, 'https://example.com/img2.jpg', (SELECT id FROM piso WHERE direccion = 'Av. Siempre Viva 742'), (SELECT id FROM user_entity WHERE username = 'propietario1')),
       (gen_random_uuid(), 'Espacioso y moderno, cerca del parque.', 900.00, 'https://example.com/img3.jpg', (SELECT id FROM piso WHERE direccion = 'Calle Luna 12'), (SELECT id FROM user_entity WHERE username = 'propietario2')),
       (gen_random_uuid(), 'Piso bien ubicado, acceso a transporte público.', 700.00, 'https://example.com/img4.jpg', (SELECT id FROM piso WHERE direccion = 'Calle Sol 8'), (SELECT id FROM user_entity WHERE username = 'propietario2')),
       (gen_random_uuid(), 'A un paso del centro histórico.', 550.00, 'https://example.com/img5.jpg', (SELECT id FROM piso WHERE direccion = 'Calle Mar 33'), (SELECT id FROM user_entity WHERE username = 'propietario3')),
       (gen_random_uuid(), 'Ideal para familias grandes. Con jardín.', 1200.00, 'https://example.com/img6.jpg', (SELECT id FROM piso WHERE direccion = 'Av. Montaña 45'), (SELECT id FROM user_entity WHERE username = 'propietario4')),
       (gen_random_uuid(), 'Apartamento reformado en zona céntrica.', 680.00, 'https://example.com/img7.jpg', (SELECT id FROM piso WHERE direccion = 'Calle Río 27'), (SELECT id FROM user_entity WHERE username = 'propietario5'));
