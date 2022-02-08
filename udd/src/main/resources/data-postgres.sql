insert into education (level, name) values (1, '4 razreda osnovne skole');
insert into education (level, name) values (2, 'osnovna skola');
insert into education (level, name) values (3, 'trogodisnja srednja skola');
insert into education (level, name) values (4, 'srednja skola');
insert into education (level, name) values (5, 'strukovne studije');
insert into education (level, name) values (6, 'osnovne akademske studije');
insert into education (level, name) values (7, 'master studije');
insert into education (level, name) values (8, 'doktorske studije');

insert into role (name) values ('ROLE_HR');

-- password: hruser
insert into users (username, password, enabled, last_password_reset_date) values ('hruser', '$2a$12$LTEuiEJHd9fCkntqxw6dxufGpMuNm/3f7yRa8tVNsR33yjj2eo8p6', true, '2017-10-01 21:58:58.508-07');

insert into user_role (user_id, role_id) values (1, 1);