--users creation

INSERT INTO users (user_id, order_id, first_name, last_name, address_line_1, address_line_2, post_code)
VALUES
(2001, 3001, 'John', 'Smith', '10 Something Road', 'London', 'SW1'),
(2002, 3002, 'Paul', 'Roberts', '20 Long Road', 'London', 'SW2'),
(2003, 3003, 'Mary', 'Klein', '30 Anywhere Street', 'London', 'SW3'),
(2004, 3004, 'Jane', 'Terry', '40 Lost Avenue', 'London', 'SW4'),
(2005, 3005, 'Jack', 'Murry', '50 Somewhere Close', 'London', 'SW5');

INSERT INTO orders (order_id, user_id, total_items, total_price, order_date)
VALUES
(3001, 2001, 1, 9.99, '2022-03-01 09:00:00'),
(3002, 2002, 2, 19.99, '2022-03-02 10:00:00'),
(3003, 2003, 3, 29.99, '2022-03-03 11:00:00'),
(3004, 2004, 4, 39.99, '2022-03-04 12:00:00'),
(3005, 2005, 5, 49.99, '2022-03-05 13:00:00');