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
VALUES 
(gen_random_uuid(), 'Calle Falsa 123', 80.0, 3, 'Luminoso piso de tres habitaciones con amplio salón, cocina equipada y balcón. Perfecto para familias o parejas que buscan comodidad y cercanía a servicios y transporte.', (SELECT id FROM user_entity WHERE username = 'propietario1')),
       (gen_random_uuid(), 'Av. Siempre Viva 742', 65.0, 2, 'Apartamento moderno y reformado, ideal para estudiantes o profesionales. Dispone de dos habitaciones, cocina funcional y excelente ubicación cerca de universidades.', (SELECT id FROM user_entity WHERE username = 'propietario1')),
       (gen_random_uuid(), 'Calle Luna 12', 90.0, 4, 'Espacioso piso familiar con cuatro habitaciones, dos baños y salón con grandes ventanales. Zona tranquila, cerca de parques y colegios, ideal para familias grandes.', (SELECT id FROM user_entity WHERE username = 'propietario2')),
       (gen_random_uuid(), 'Calle Sol 8', 70.0, 2, 'Piso acogedor con dos habitaciones, cocina equipada y baño renovado. Excelente comunicación con transporte público y servicios a pocos minutos caminando.', (SELECT id FROM user_entity WHERE username = 'propietario2')),
       (gen_random_uuid(), 'Calle Mar 33', 55.0, 1, 'Apartamento céntrico y funcional, perfecto para una persona sola o pareja. Cuenta con salón moderno, cocina equipada y acceso rápido a la zona histórica.', (SELECT id FROM user_entity WHERE username = 'propietario3')),
       (gen_random_uuid(), 'Av. Montaña 45', 110.0, 5, 'Gran piso con cinco habitaciones, jardín privado y terraza. Ideal para familias numerosas que buscan espacio, tranquilidad y zonas verdes en el entorno.', (SELECT id FROM user_entity WHERE username = 'propietario4')),
       (gen_random_uuid(), 'Calle Río 27', 60.0, 2, 'Apartamento reformado en el centro, dos habitaciones, cocina americana y salón luminoso. Perfecto para quienes desean vivir cerca de todo en un ambiente moderno.', (SELECT id FROM user_entity WHERE username = 'propietario5'));

-- Insertar 7 anuncios (uno por piso)
INSERT INTO anuncio (id, descripcion, precio, url_imagen, piso_id, propietario_id)
VALUES 
(gen_random_uuid(), 'Piso ideal para parejas jóvenes, tres habitaciones, cocina equipada, salón amplio y balcón. Muy luminoso, cerca de transporte, supermercados y zonas verdes. Perfecto para quienes valoran la luz y la comodidad.', 80.00, 'https://decofilia.com/wp-content/uploads/2016/09/decorar-piso-vacacional-14.jpg', (SELECT id FROM piso WHERE direccion = 'Calle Falsa 123'), (SELECT id FROM user_entity WHERE username = 'propietario1')),
(gen_random_uuid(), 'Apartamento luminoso para estudiantes, dos habitaciones, escritorio, wifi rápido y cocina moderna. Edificio seguro y tranquilo, servicios a pie de calle. Ideal para estudiar y disfrutar de la vida universitaria.', 55.00, 'https://www.lavozdelanzarote.com/uploads/s1/26/54/13/2/q6a6041_1_766x440.jpeg', (SELECT id FROM piso WHERE direccion = 'Av. Siempre Viva 742'), (SELECT id FROM user_entity WHERE username = 'propietario1')),
(gen_random_uuid(), 'Piso moderno y espacioso, cuatro habitaciones, cocina equipada, dos baños, salón con ventanales y garaje. Cerca de parque y zonas verdes. Ideal para familias o grupos que buscan amplitud y comodidad.', 120.00, 'https://img.holidu.com/images/5281d0c2-f9d7-40ba-9332-6ee25f770475/l.avif', (SELECT id FROM piso WHERE direccion = 'Calle Luna 12'), (SELECT id FROM user_entity WHERE username = 'propietario2')),
(gen_random_uuid(), 'Piso bien ubicado, acceso a transporte público, dos habitaciones, salón acogedor, cocina funcional y baño renovado. Zona tranquila con supermercados y restaurantes. Perfecto para movilidad y vida urbana.', 75.00, 'https://www.livitum.com/blogs/6/7/7/tx_d0dd8ab3-61cb-4adf-bc51-7aee3ba1daa1_decoracion_piso_turistico.jpg', (SELECT id FROM piso WHERE direccion = 'Calle Sol 8'), (SELECT id FROM user_entity WHERE username = 'propietario2')),
(gen_random_uuid(), 'Apartamento céntrico, una habitación amplia, salón moderno, cocina equipada y baño completo. Vive cerca de museos, restaurantes y cultura, sin renunciar a la tranquilidad de un hogar acogedor.', 100.00, 'https://img.holidu.com/images/44c0e3ba-ed53-40a8-912d-873b97a39f1b/l.avif', (SELECT id FROM piso WHERE direccion = 'Calle Mar 33'), (SELECT id FROM user_entity WHERE username = 'propietario3')),
(gen_random_uuid(), 'Piso para familias grandes, cinco habitaciones, jardín privado y terraza. Cocina amplia, salón cómodo. Zona residencial tranquila, cerca de colegios y parques. Espacio y confort para toda la familia.', 60.00, 'https://blog.destinyhome.es/wp-content/uploads/2024/05/apartamento-vacacional-fuerteventura.jpg', (SELECT id FROM piso WHERE direccion = 'Av. Montaña 45'), (SELECT id FROM user_entity WHERE username = 'propietario4')),
(gen_random_uuid(), 'Apartamento reformado en zona céntrica, dos habitaciones, cocina americana, baño moderno y salón luminoso. Servicios, tiendas y transporte a pocos pasos. Ideal para profesionales o parejas.', 90.00, 'https://cdn.hometogo.net/large/v1/004/9b2/4fe48d8cc7cf6cf1cc668b8b44.jpg', (SELECT id FROM piso WHERE direccion = 'Calle Río 27'), (SELECT id FROM user_entity WHERE username = 'propietario5'));
