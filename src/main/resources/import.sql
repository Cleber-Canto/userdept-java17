-- Insere departamentos
INSERT INTO tb_department(name) VALUES ('Gestão');
INSERT INTO tb_department(name) VALUES ('Informática');

-- Insere usuários, agora incluindo phone e password
INSERT INTO tb_user(department_id, name, email, phone, password) VALUES (1, 'Maria Brown', 'maria@gmail.com', '988888888', '123456');
INSERT INTO tb_user(department_id, name, email, phone, password) VALUES (1, 'Bob', 'bob@gmail.com', '977777777', '654321');
INSERT INTO tb_user(department_id, name, email, phone, password) VALUES (2, 'Alex', 'alex@gmail.com', '966666666', 'abcdef');
INSERT INTO tb_user(department_id, name, email, phone, password) VALUES (2, 'Ana', 'ana@gmail.com', '955555555', 'fedcba');
