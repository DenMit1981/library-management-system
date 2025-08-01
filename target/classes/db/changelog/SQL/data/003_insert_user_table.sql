INSERT INTO users (id, name, email)
VALUES
(1, 'Ivan Petrov', 'petrov@mail.com'),
(2, 'Olga Sidorova', 'sidorova@mail.com'),
(3, 'Wei Zhang', 'zhang@mail.com'),
(4, 'Liang Tan', 'tan@mail.com'),
(5, 'Xiu Ying', 'ying@mail.com'),
(6, 'Ahmad Faizal', 'faizal@mail.com'),
(7, 'Nur Aisyah', 'aisyah@mail.com');

SELECT setval('user_id_seq', (SELECT MAX(id) from users));

