INSERT INTO books (id, isbn, title, author)
VALUES
(1, '978-9833789123', 'Hikayat Hang Tuah', 'Tun Seri Lanang'),
(2, '978-9834501234', 'Salina', 'A. Samad Said'),
(3, '978-9836201456', 'Ranjau Sepanjang Jalan', 'Shahnon Ahmad'),
(4, '978-9831357890', 'Tenggelamnya Kapal Van Der Wijck', 'Hamka'),
(5, '978-9832284111', 'Diari Seorang Lelaki', 'Syed Alwi'),
(6, '978-9810749812', 'If We Dream Too Long', 'Goh Poh Seng'),
(7, '978-9810987654', 'A Place Where I Belong', 'Rex Shelley'),
(8, '978-9811234567', 'The Art of Charlie Chan Hock Chye', 'Sonny Liew'),
(9, '978-9815432198', 'The Space Between the Raindrops', 'Justin Ker'),
(10, '978-9816543210', 'Ministry of Moral Panic', 'Amanda Lee Koe'),
(11, '978-0140449266', 'War and Peace', 'Leo Tolstoy'),
(12, '978-0140449334', 'Crime and Punishment', 'Fyodor Dostoevsky'),
(13, '978-0140186390', 'The Master and Margarita', 'Mikhail Bulgakov'),
(14, '978-0486456583', 'Eugene Onegin', 'Alexander Pushkin'),
(15, '978-0140447927', 'The Brothers Karamazov', 'Fyodor Dostoevsky'),
(16, '978-0451524935', '1984', 'George Orwell'),
(17, '978-0743273565', 'The Great Gatsby', 'F. Scott Fitzgerald'),
(18, '978-0061120084', 'To Kill a Mockingbird', 'Harper Lee'),
(19, '978-0544003415', 'The Hobbit', 'J.R.R. Tolkien'),
(20, '978-0307277671', 'The Road', 'Cormac McCarthy');

SELECT setval('book_id_seq', (SELECT MAX(id) from books));


