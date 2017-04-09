DELETE FROM meals;
DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password');

INSERT INTO users (name, email, password)
VALUES ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (user_id, date_time, description, calories) VALUES
  (100000,'May 30 10:00:00 2015','Завтрак',500),
  (100000,'May 30 13:00:00 2015','Обед',1000),
  (100000,'May 30 20:00:00 2015','Ужин',500),
  (100000,'May 31 10:00:00 2015','Завтрак',1000),
  (100000,'May 31 13:00:00 2015','Обед',500),
  (100000,'May 31 20:00:00 2015','Ужин',510),
  (100001,'Jun 1 14:00:00 2015','Админ ланч',510),
  (100001,'Jun 1 21:00:00 2015','Админ ужин',1500);