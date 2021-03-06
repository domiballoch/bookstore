--books creation

INSERT INTO book (isbn, category, title, author, price, stock)
VALUES
(1001, 'FICTION', 'Tall Tales', 'Mr Fredrikson', 4.99, 25),
(1002, 'FICTION', 'Short Tales', 'Mr Fredrikson', 24.99, 50),
(1003, 'TRAVEL', 'A Foreign Land', 'Mr Kite', 11.99, 10),
(1004, 'ROMANCE', 'An English Rose', 'Mrs Juliet', 7.99, 20),
(1005, 'HORROR', 'The Crab Man', 'Mr Gustavo', 14.99, 100);

INSERT INTO users (user_id, first_name, last_name, address_line_1, address_line_2, post_code)
VALUES
(2001, 'John', 'Smith', '10 Something Road', 'London', 'SW1'),
(2002, 'Paul', 'Roberts', '20 Long Road', 'London', 'SW2'),
(2003, 'Mary', 'Klein', '30 Anywhere Street', 'London', 'SW3'),
(2004, 'Jane', 'Terry', '40 Lost Avenue', 'London', 'SW4'),
(2005, 'Jack', 'Murry', '50 Somewhere Close', 'London', 'SW5');

INSERT INTO order_details (order_details_id, fk_user_id, fk_isbn, order_date)
VALUES
(3001, 2001, 1001, '2022-03-01 09:00:00'),
(3002, 2002, 1002, '2022-03-02 10:00:00'),
(3003, 2003, 1003, '2022-03-03 11:00:00'),
(3004, 2004, 1004, '2022-03-04 12:00:00'),
(3005, 2005, 1005, '2022-03-05 13:00:00');
